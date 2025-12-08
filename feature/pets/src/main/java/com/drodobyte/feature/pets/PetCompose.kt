@file:Suppress("FunctionNaming", "MagicNumber")

package com.drodobyte.feature.pets

import android.util.Size
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.drodobyte.core.data.model.Pet

@Composable
internal fun Pet(pet: Pet, edited: (Pet) -> Unit, onClickImage: () -> Unit) =
    Surface(
        modifier = Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        with(pet) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    url = image,
                    resolution = Size(240, 240),
                    modifier = Modifier
                        .size(256.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .align(Alignment.CenterHorizontally),
                    clicked = { onClickImage() }
                )
                Space()
                Edit(R.string.name, name) {
                    edited(pet.copy(name = it))
                }
                Space()
                Edit(R.string.description, description) {
                    edited(pet.copy(description = it))
                }
                Space()
                Edit(R.string.last_known_location, location ?: "") {
                    edited(pet.copy(location = it))
                }
            }
        }
    }

@Composable
private fun Space() = Spacer(Modifier.padding(bottom = 16.dp))
