package com.drodobyte.feature.pets

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.drodobyte.feature.pets.PetsViewModel.State
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION

@Composable
@PreviewAll
private fun NoUserInput() {
    PetsScreen(State(), {}, {}, {}, {})
}

@Composable
@PreviewAll
private fun WithUserInput() {

}

@Retention(BINARY)
@Target(ANNOTATION_CLASS, FUNCTION)
@PreviewFontScale
@PreviewLightDark
@PreviewScreenSizes
@PreviewDynamicColors
private annotation class PreviewAll
