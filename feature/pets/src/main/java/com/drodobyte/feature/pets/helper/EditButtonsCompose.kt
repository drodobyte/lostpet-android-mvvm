@file:Suppress("FunctionNaming")

package com.drodobyte.feature.pets.helper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Filter.All
import com.drodobyte.core.data.model.Filter.Found
import com.drodobyte.core.data.model.Filter.Lost
import com.drodobyte.feature.pets.R

@Composable
internal fun EditButtons(
    filtered: (Filter) -> Unit,
    new: () -> Unit,
) {
    var expand by remember { mutableStateOf(false) }

    Column {
        if (expand) {
            FloatingActionButton(
                onClick = {
                    expand = false
                    filtered(All)
                }) {
                Text(stringResource(R.string.all))
            }
            Spacer(Modifier.height(8.dp))
            FloatingActionButton(
                onClick = {
                    expand = false
                    filtered(Found)
                }) {
                Text(stringResource(R.string.found))
            }
            Spacer(Modifier.height(8.dp))
            FloatingActionButton(
                onClick = {
                    expand = false
                    filtered(Lost)
                }) {
                Text(stringResource(R.string.lost))
            }
        } else {
            FloatingActionButton({ expand = true }) {
                Icon(Icons.Filled.Search, "Filter Pets")
            }
        }
        Spacer(Modifier.height(16.dp))
        FloatingActionButton(onClick = new) {
            Icon(Icons.Filled.Add, contentDescription = "Add New Pet")
        }
    }
}
