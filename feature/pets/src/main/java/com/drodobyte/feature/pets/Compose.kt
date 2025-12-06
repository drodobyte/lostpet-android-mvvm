@file:Suppress("FunctionNaming")

package com.drodobyte.feature.pets

import android.util.Size
import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole.Detail
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.coroutines.launch

@Composable
fun Image(
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

@Composable
fun Edit(
    @StringRes label: Int,
    value: String,
    readOnly: Boolean = false,
    changed: (String) -> Unit = {}
) {
    var text by remember(value) { mutableStateOf(value) }
    OutlinedTextField(
        value = text,
        readOnly = readOnly,
        onValueChange = { text = it; changed(it) },
        label = { Text(stringResource(label)) }
    )
}

@Preview(
    name = "Small scale",
    group = "Font scales",
    fontScale = .5f
)
@Preview(
    name = "Big scale",
    group = "Font scales",
    fontScale = 1.5f
)
annotation class FontScalesPreview

@FontScalesPreview
@PreviewScreenSizes
@PreviewLightDark
@Composable
@Preview
fun Fail() =
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(android.R.drawable.stat_notify_error),
                modifier = Modifier
                    .size(256.dp)
                    .background(Color.Yellow),
                contentDescription = "fail"
            )
            Text(stringResource(R.string.error), color = Color.Red)
        }
    }


@Composable
@Preview
fun Empty() =
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

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetail(
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
