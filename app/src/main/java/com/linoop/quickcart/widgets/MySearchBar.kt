package com.linoop.quickcart.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.linoop.quickcart.R
import com.linoop.quickcart.ui.theme.Red
import com.linoop.quickcart.utils.onSearch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawSearchBar(
    query: MutableState<String>,
    @StringRes errorTextId: Int?,
    @StringRes placeholder: Int,
    @StringRes contentDesc: Int,
    onSearch: onSearch
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            query = query.value,
            onQueryChange = { str -> query.value = str },
            onSearch = { str -> onSearch.invoke(str) },
            active = false,
            onActiveChange = { },
            placeholder = { Text(text = stringResource(id = placeholder)) },
            trailingIcon = {
                IconButton(onClick = { onSearch.invoke(query.value) }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = contentDesc)
                    )
                }
            },
            content = {},
            modifier = Modifier
                .fillMaxWidth()

        )
        if (errorTextId != null) Text(text = stringResource(id = errorTextId), color = Red)
    }
}