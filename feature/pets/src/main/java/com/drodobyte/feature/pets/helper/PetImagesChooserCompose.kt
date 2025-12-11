@file:Suppress("FunctionNaming", "MagicNumber")

package com.drodobyte.feature.pets.helper

import android.util.Size
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
internal fun PetImagesChooser(
    images: List<String>,
    selected: (String) -> Unit,
) {
    Dialog(onDismissRequest = { }) {
        LazyVerticalGrid(
            modifier = Semantic.ImageChooser.mod,
            verticalArrangement = spacedBy24,
            horizontalArrangement = spacedBy24,
            columns = GridCells.FixedSize(128.dp),
        ) {
            items(images) {
                PetImage(it) { selected(it) }
            }
        }
    }
}

@Composable
private fun PetImage(image: String, clicked: () -> Unit) {
    Box(modifier = roundedCornerClipMod) {
        Image(
            url = image,
            resolution = Size(120, 120),
            clicked = { clicked() },
            modifier = Modifier
                .size(128.dp)
                .align(Alignment.Center),
        )
    }
}

private val roundedCornerShape = RoundedCornerShape(32.dp)
private val roundedCornerClipMod = Modifier.clip(roundedCornerShape)
private val spacedBy24 = Arrangement.spacedBy(24.dp)
