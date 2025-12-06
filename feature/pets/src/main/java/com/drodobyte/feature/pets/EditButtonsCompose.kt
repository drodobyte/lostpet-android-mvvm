@file:Suppress("FunctionNaming")

package com.drodobyte.feature.pets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.drodobyte.core.data.model.Filter
import com.drodobyte.core.data.model.Filter.All
import com.drodobyte.core.data.model.Filter.Found
import com.drodobyte.core.data.model.Filter.Lost

@Composable
internal fun EditButtons(
    filter: Filter,
    filtered: (Filter) -> Unit,
    new: () -> Unit,
) {
    var current by remember { mutableStateOf(filter) }

    Column {
        SearchButton(current) { current = it; filtered(it) }
        Spacer(Modifier.size(16.dp))
        NewPetButton(new)
    }
}

@Composable
private fun SearchButton(filter: Filter, filtered: (Filter) -> Unit) {
    var expand by remember { mutableStateOf(false) }
    if (expand) {
        AllFilterButtons(filter = filter, filtered = { expand = false; filtered(it) })
    } else {
        FloatingActionButton({ expand = true }) { Icon(Icons.Filled.Search, "Filter Pets") }
    }
}

@Composable
private fun AllFilterButtons(filter: Filter, filtered: (Filter) -> Unit) {
    var now by remember { mutableStateOf(filter) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Filter(R.string.all) { now = All; filtered(All) }
        Filter(R.string.found) { now = Found; filtered(Found) }
        Filter(R.string.lost) { now = Lost; filtered(Lost) }
    }
}

@Composable
private fun Filter(@StringRes text: Int, clicked: () -> Unit) =
    FloatingActionButton(onClick = clicked) { Text(stringResource(text)) }

@Composable
private fun NewPetButton(clicked: () -> Unit) =
    FloatingActionButton(onClick = clicked) {
        Icon(Icons.Filled.Add, contentDescription = "Add New Pet")
    }
