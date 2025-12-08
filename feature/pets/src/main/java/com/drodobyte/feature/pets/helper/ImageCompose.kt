package com.drodobyte.feature.pets.helper

import android.util.Size
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.drodobyte.feature.pets.R

@Composable
internal fun Image(
    url: String,
    resolution: Size,
    modifier: Modifier = Modifier,
    clicked: () -> Unit = {}
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(resolution.width, resolution.height)
            .crossfade(true).build(),
        placeholder = painterResource(id = R.drawable.ic_download),
        error = painterResource(id = R.drawable.ic_pets),
        contentDescription = "url",
        contentScale = ContentScale.Crop,
        modifier = modifier then Modifier.clickable { clicked() }
    )
}
