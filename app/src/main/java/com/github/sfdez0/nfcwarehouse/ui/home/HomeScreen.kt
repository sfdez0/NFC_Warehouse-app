package com.github.sfdez0.nfcwarehouse.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.sfdez0.nfcwarehouse.R
import com.github.sfdez0.nfcwarehouse.data.model.Location
import com.github.sfdez0.nfcwarehouse.ui.theme.NFCWarehouseTheme

/**
 * Stateful route for the Home Screen.
 * * This composable acts as a bridge between the [HomeViewModel] and the UI.
 * It collects the locations state flow and passes it down to the stateless [HomeScreen].
 *
 * @param viewModel The [HomeViewModel] responsible for managing the state of this screen.
 */
@Composable
fun HomeRoute(onNavigateToLocation: () -> Unit, viewModel: HomeViewModel = viewModel()) {
    val locations by viewModel.locations.collectAsState()

    HomeScreen(
        locationList = locations,
        onNavigateToLocation = onNavigateToLocation
    )
}

/**
 * Stateless composable for the Home Screen.
 * This screen displays the main UI, including a top app bar and a
 * scrollable list of [Location] items.
 *
 * @param locationList List of [Location] elements
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(locationList: List<Location>, onNavigateToLocation: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NFC Warehouse") },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
            )
        },
        // TODO floatingActionButton
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = locationList,
                // Use the ID of each location as the key
                key = { location -> location.id },
            ) { location ->
                LocationListItem(location, onNavigateToLocation)
            }
        }
    }
}

/**
 * Composable for a single [Location] item in the list.
 *
 * @param location The [Location] to display
 */
@Composable
fun LocationListItem(location: Location, onNavigateToLocation: () -> Unit) {
    // State to track if the item is expanded or not
    var expanded by rememberSaveable { mutableStateOf(false) }

    // Card for the location item
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .animateContentSize(),
    ) {
        Column(
            modifier =
                Modifier
                    .clickable { expanded = !expanded }
                    .padding(16.dp)
                    .fillMaxWidth(),
        ) {
            // Name of the location
            Text(
                text = location.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            // If expanded, display the description of the location
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text =
                        location.description
                            ?: stringResource(R.string.home_location_default_description),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onNavigateToLocation
                ) {
                    Text("Ver")
                }
            }
        }
    }
}

/**
 * Preview for the [HomeScreen] composable.
 */
@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    // Mock locations for preview
    val mockLocations =
        listOf(
            Location(id = 1, name = "Example 1", storageSpaces = mutableListOf()),
            Location(id = 2, name = "Example 2", storageSpaces = mutableListOf()),
            Location(id = 3, name = "Example 3", storageSpaces = mutableListOf()),
            Location(id = 4, name = "Example 4", storageSpaces = mutableListOf()),
            Location(id = 5, name = "Example 5", storageSpaces = mutableListOf()),
            Location(id = 6, name = "Example 6", storageSpaces = mutableListOf()),
        )

    NFCWarehouseTheme {
        HomeScreen(
            locationList = mockLocations,
            onNavigateToLocation = {})
    }
}
