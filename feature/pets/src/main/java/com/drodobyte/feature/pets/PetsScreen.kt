package com.drodobyte.feature.pets

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Pet
import com.drodobyte.feature.pets.PetsViewModel.State
import com.drodobyte.feature.pets.util.IntEditField
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
            onSelected = { viewModel.selected(it) },
        )
    }
}

@Composable
internal fun PetsScreen(
    state: State,
    onNewPet: () -> Unit,
    onFilter: (Filter) -> Unit,
    onSelected: (Pet) -> Unit,
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
                .padding(padding),
            contentAlignment = Alignment.BottomEnd
        ) {
            ListDetail(
                {
                    Pets(
                        pets = state.pets,
                        onSelected = { onSelected(it) },
                    )
                },
                detail = {
                    state.selected?.let { Pet(state.selected, edited = {}) } ?: Empty()
                },
                detailId = state.selected?.id,
                back = {}
            )
            EditButtons(
                Filter.All,
                filtered = { onFilter(it) },
                new = { onNewPet() }
            )
        }

        val scope = rememberCoroutineScope()
        val txt = stringResource(R.string.error)
        state.errors?.let {
            LaunchedEffect(state.errors) {
                scope.launch {
                    snackbarHostState.showSnackbar(txt)
                }
            }
        }
    }
}

@Composable
private fun State.Pets(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onSelected: (Pet?) -> Unit,
    onWeight: (Int?) -> Unit,
) =
    Column(modifier.padding(24.dp)) {

        IntakeMessage(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(),
        )
        Spacer(Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.Top) {
            IntEditField(
                number = 0,
                label = R.string.pets,
                placeholder = R.string.lost,
                onChange = onWeight,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(12.dp))
//            FoodSearch(
//                query = search,
//                foods = foods,
//                onSearch = onSearch,
//                onSelected = onSelected,
//                modifier = Modifier
//                    .offset(y = (-36).dp)
//                    .weight(3f)
//            )
        }
    }

@Composable
private fun State.IntakeMessage(
    modifier: Modifier = Modifier
) =
    Card(modifier, elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)) {
        Crossfade(true) { ok ->
            val txt = if (ok) {
                stringResource(
                    R.string.lost,
                )
            } else {
                stringResource(R.string.error)
            }

            Text(
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Justify,
                text = txt
            )
        }
    }
