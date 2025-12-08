package com.drodobyte.feature.pets.helper

import androidx.annotation.StringRes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

@Composable
internal fun Edit(
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
