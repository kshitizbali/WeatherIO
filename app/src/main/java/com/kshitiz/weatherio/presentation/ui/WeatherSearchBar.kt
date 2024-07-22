package com.kshitiz.weatherio.presentation.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kshitiz.weatherio.R
import com.kshitiz.weatherio.domain.model.City

/**
 * Composable UI component implements a search bar which parses the json file from raw folder & displays the list of cities
 * matching the search text. Also has an onClickListener implemented.
 * @param onCityClick click listener when a city is selected after searching.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherSearchBar(onCityClick: (String) -> Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val cities = remember {
        parseJsonFromRawFile(context = context)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchText = remember { mutableStateOf("") }

    val filteredCities = cities.filter {
        it.city.contains(
            searchText.value,
            ignoreCase = true
        ) && searchText.value.length >= 2
    }

    Surface(
        modifier = Modifier
            .padding(16.dp,16.dp,16.dp,8.dp)
            .clip(shape = RoundedCornerShape(20.dp))
    ) {
        Column {
            TextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                label = { Text("Search City") },
                modifier = Modifier.fillMaxWidth()
            )
            if (searchText.value.isNotEmpty()) {
                LazyColumn {
                    items(filteredCities.size) { city ->
                        val citySuggestion = filteredCities[city]
                        CitySuggestion(
                            city = citySuggestion,
                            onCityClick = onCityClick,
                            searchText,
                            keyboardController = keyboardController,
                            focusManager = focusManager
                        )
                    }
                }
            }

        }
    }
}

/**
 * UI Component text which displays the city suggestion.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CitySuggestion(
    city: City,
    onCityClick: (String) -> Unit,
    searchQueryState: MutableState<String>,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    Text(
        text = "${city.city}, ${city.state}",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onCityClick(city.city)
                keyboardController?.hide()
                searchQueryState.value = ""
                focusManager.clearFocus()
            }
    )
}

@Preview
@Composable
fun PreviewSearchBar() {
    WeatherSearchBar(onCityClick = {})
}

/**
 * Helper function to parse the json file from the raw folder and create a list of cities.
 * Can be moved to a Utils class but keeping it here as private to avoid any other class
 * accessing this.
 */
private fun parseJsonFromRawFile(context: Context): List<City> {
    val jsonData = context.resources.openRawResource(R.raw.cities).bufferedReader()
        .use { it.readText() }
    val gson = Gson()
    val listType = object : TypeToken<List<City>>() {}.type
    return gson.fromJson(jsonData, listType)
}