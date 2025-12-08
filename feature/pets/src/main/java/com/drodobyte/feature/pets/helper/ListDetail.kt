package com.drodobyte.feature.pets.helper

import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole.Detail
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun ListDetail(
    list: @Composable () -> Unit,
    detail: (@Composable () -> Unit)?,
    detailId: Long?,
    back: () -> Unit
) =
    with(rememberListDetailPaneScaffoldNavigator<Any>()) {
        val scope = rememberCoroutineScope()

        LaunchedEffect(detailId) {
            scope.launch {
                if (currentDestination?.contentKey != detailId && detail != null) {
                    navigateTo(Detail, detailId)
                }
            }
        }

        BackHandler(canNavigateBack()) {
            back()
            scope.launch {
                navigateBack()
            }
        }

        ListDetailPaneScaffold(
            directive = scaffoldDirective,
            value = scaffoldValue,
            listPane = { AnimatedPane { list() } },
            detailPane = { detail?.let { AnimatedPane { it() } } },
        )
    }
