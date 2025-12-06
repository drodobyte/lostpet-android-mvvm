package com.drodobyte.feature.pets.util

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions.Companion.Default
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import kotlinx.coroutines.delay

@Composable
internal fun IntEditField(
    number: Int?,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    onChange: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    var str by remember { mutableStateOf(number) }
    LaunchedEffect(str) {
        delay(300) // OutlinedTextField bug fix
        onChange(str)
    }
    TextField(
        modifier = modifier,
        singleLine = true,
        value = str?.toString() ?: "",
        onValueChange = { str = it.toIntOrNull() },
        label = { Text(stringResource(label)) },
        placeholder = { Text(stringResource(placeholder)) },
        keyboardOptions = Default.copy(keyboardType = Number)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchBar(
    query: String,
    @StringRes placeholder: Int,
    results: List<String>,
    onSearch: (String) -> Unit,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    var txt by remember { mutableStateOf(query) }
    LaunchedEffect(txt) { // fix bug InputField
        delay(300)
        onSearch(txt)
    }
    SearchBar(
        modifier = modifier.then(Modifier.onFocusChanged { expanded = it.hasFocus }),
        inputField = {
            SearchBarDefaults.InputField(
                query = txt,
                onQueryChange = { txt = it },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text(stringResource(placeholder)) }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            results.forEach {
                ListItem(
                    headlineContent = { Text(it) },
                    modifier = Modifier
                        .clickable {
                            txt = it
                            onSelected(it)
                            expanded = false
                        }
                        .fillMaxWidth()
                )
            }
        }
    }
}
