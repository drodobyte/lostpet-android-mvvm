package com.drodobyte.feature.pets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Pet
import com.drodobyte.feature.pets.PetsViewModel.State
import kotlinx.coroutines.launch

@Composable
fun PetsScreen(
    viewModel: PetsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle(State())

    Column {
        PetsScreen(
            state,
            onNewPet = { viewModel.newPet() },
            onFilter = { viewModel.filtered(it) },
            onSelectedPet = { viewModel.selected(it) },
            onEdited = { viewModel.edited(it) }
        )
    }
}

@Composable
internal fun PetsScreen(
    state: State,
    onNewPet: () -> Unit,
    onFilter: (Filter) -> Unit,
    onSelectedPet: (Pet?) -> Unit,
    onEdited: (Pet) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            var showImages by remember { mutableStateOf(false) }

            ListDetail(
                list = {
                    Pets(state.pets) { onSelectedPet(it) }
                },
                detail = {
                    state.selectedPet?.let {
                        Pet(
                            pet = state.selectedPet,
                            edited = onEdited,
                            onClickImage = { showImages = true }
                        )
                    } ?: Empty()
                },
                detailId = state.selectedPet?.id,
                back = { onSelectedPet(null) }
            )
            EditButtons(
                filtered = { onFilter(it) },
                new = { onNewPet() }
            )

            if (showImages) {
                PetImagesChooser(
                    images = state.images,
                    selected = { image ->
                        showImages = false
                        state.selectedPet?.let { onEdited(it.copy(image = image)) }
                    }
                )
            }
        }

        val scope = rememberCoroutineScope()
        val txt = stringResource(R.string.error)
        if (state.error) {
            LaunchedEffect(Unit) {
                scope.launch {
                    snackbarHostState.showSnackbar(txt)
                }
            }
        }
    }
}
