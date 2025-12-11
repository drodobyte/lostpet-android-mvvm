package com.drodobyte.feature.pets.helper

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

enum class Semantic {
    Filter,
    NewPet,
    All,
    Found,
    Lost,
    NoPetsToShow,
    Pet,
    Badge,
    EditName,
    EditDescription,
    EditImage,
    EditLocation,
    ImageChooser,
    ;

    val mod get() = Modifier.testTag(name)
}
