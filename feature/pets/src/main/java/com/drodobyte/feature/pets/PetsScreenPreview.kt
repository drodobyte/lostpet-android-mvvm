package com.drodobyte.feature.pets

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.drodobyte.core.data.model.Pet
import com.drodobyte.feature.pets.PetsViewModel.State
import kotlin.annotation.AnnotationRetention.BINARY
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.FUNCTION
import kotlin.random.Random

@Composable
@PreviewAll
private fun NoPets() {
    PetsScreen(
        State(
            pets = emptyList(),
            selectedPet = null,
        ),
        {}, {}, {}, {})
}

@Composable
@PreviewAll
private fun WithPets() {
    PetsScreen(
        State(
            pets = listOf(pet(1), pet(2), pet(3)),
            selectedPet = null,
        ), {}, {}, {}, {})
}

@Composable
@PreviewAll
private fun WithPetsAndSelectedOne() {
    PetsScreen(
        State(
            pets = listOf(pet(1), pet(2), pet(3)),
            selectedPet = pet(1),
        ), {}, {}, {}, {})
}

private fun pet(i: Long): Pet =
    Pet(i, "name $i", "description $i", location = if (Random.nextBoolean()) "loc" else "")

@Retention(BINARY)
@Target(ANNOTATION_CLASS, FUNCTION)
@PreviewFontScale
@PreviewLightDark
@PreviewScreenSizes
@PreviewDynamicColors
private annotation class PreviewAll
