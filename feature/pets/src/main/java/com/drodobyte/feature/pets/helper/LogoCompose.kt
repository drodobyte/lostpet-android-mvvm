package com.drodobyte.feature.pets.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.drodobyte.feature.pets.R

@Composable
internal fun Logo() =
    Surface(
        Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(R.drawable.ic_pets),
                modifier = Modifier.size(256.dp),
                contentDescription = "empty"
            )
        }
    }
