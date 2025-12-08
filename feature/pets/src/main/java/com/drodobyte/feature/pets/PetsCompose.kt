@file:Suppress("FunctionNaming", "MagicNumber")

package com.drodobyte.feature.pets

import android.util.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.drodobyte.core.data.model.Pet

@Composable
internal fun Pets(
    pets: List<Pet>,
    onSelected: (Pet) -> Unit
) =
    LazyVerticalGrid(
        verticalArrangement = spacedBy24,
        horizontalArrangement = spacedBy24,
        columns = GridCells.FixedSize(128.dp),
    ) {
        if (pets.isEmpty()) {
            item {
                Text(text = stringResource(R.string.no_pets_to_show))
            }
        } else {
            items(pets) {
                Pet(it, onSelected)
            }
        }
    }

@Composable
private fun Pet(pet: Pet, clicked: (Pet) -> Unit) =
    Box {
        Box(modifier = roundedCornerClipMod) {
            Image(
                url = pet.image,
                resolution = Size(120, 120),
                clicked = { clicked(pet) },
                modifier = Modifier
                    .size(128.dp)
                    .align(Alignment.Center),
            )
            Box(
                Modifier
                    .background(blackAlpha)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    color = Color.White,
                    text = pet.name,
                    modifier = Modifier
                        .clickable { clicked(pet) }
                        .align(Alignment.BottomCenter)
                )
            }
        }
        FoundBadge(pet.found, Modifier.align(Alignment.TopEnd))
    }

@Composable
private fun FoundBadge(found: Boolean, modifier: Modifier = Modifier) {
    if (found) {
        Box(
            modifier = modifier
                then roundedCornerClipMod
                .background(Color.White)
        ) {
            Icon(
                modifier = modifier,
                painter = painterResource(id = R.drawable.ic_pin),
                contentDescription = "found",
                tint = Color.Red
            )
        }
    }
}

private val roundedCornerShape = RoundedCornerShape(32.dp)
private val roundedCornerClipMod = Modifier.clip(roundedCornerShape)
private val blackAlpha = Color.Black.copy(.3f)
private val spacedBy24 = Arrangement.spacedBy(24.dp)
