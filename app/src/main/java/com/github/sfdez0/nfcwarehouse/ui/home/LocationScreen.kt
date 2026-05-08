package com.github.sfdez0.nfcwarehouse.ui.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.sfdez0.nfcwarehouse.R
import com.github.sfdez0.nfcwarehouse.data.model.Location
import com.github.sfdez0.nfcwarehouse.data.model.StorageSpace
import com.github.sfdez0.nfcwarehouse.ui.theme.NFCWarehouseTheme

/**
 * Stateful route for the Location Screen.
 * * This composable acts as a bridge between the [LocationViewModel] and the UI.
 * It collects the locations state flow and passes it down to the stateless [LocationScreen].
 *
 * @param locationId The ID of the location to display
 * @param viewModel The [LocationViewModel] responsible for managing the state of this screen.
 */
@Composable
fun LocationRoute(
    locationId: Long,
    viewModel: LocationViewModel = viewModel(),
) {
    LaunchedEffect(key1 = locationId) {
        viewModel.getLocationData(locationId)
    }

    val location by viewModel.location.collectAsState()

    LocationScreen(location)
}

/**
 * Stateless composable for the Location Screen.
 * This screen displays the location UI, including a top app bar and
 * all the information about the location.
 *
 * @param location The [Location] to display
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(location: Location?) {
    if (location == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
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
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { }, // TODO Add storage space screen
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                        contentDescription = "Add location",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White,
                    )
                }
            },
        ) { paddingValues ->
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
            ) {
                Text(
                    text = location.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ID: ${location.id}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )

                if (!location.description.isNullOrEmpty()) {
                    Text(
                        text = location.description!!,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                } else {
                    Text(
                        text = "No description available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = location.storageSpaces,
                        // Use the ID of each storage space as the key
                        key = { storageSpace -> storageSpace.id },
                    ) { location ->
                        StorageSpaceListItem(location)
                    }
                }
            }
        }
    }
}

/**
 * Composable for a single [StorageSpace] item in the list.
 *
 * @param storageSpace The [StorageSpace] to display
 */
@Composable
fun StorageSpaceListItem(storageSpace: StorageSpace) {
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
            // Name of the storage space
            Text(
                text = storageSpace.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ID
            Text(
                text = "ID: ${storageSpace.id}",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // NFC tag ID
            Text(
                text = "NFC Tag: ${storageSpace.nfcTagId}",
                style = MaterialTheme.typography.bodyMedium,
            )

            // TODO Expand logic
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        // TODO
                    },
                ) {
                    Text(stringResource(R.string.view_button))
                }
            }
        }
    }
}

/**
 * Preview for the [LocationScreen] composable.
 */
@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    val locationMock =
        Location(
            id = 1,
            name = "Example 1",
            description = "Example description",
            storageSpaces =
                mutableListOf(
                    StorageSpace(
                        id = 1,
                        name = "Example 1",
                        nfcTagId = "123456789",
                        items = mutableListOf(),
                    ),
                    StorageSpace(
                        id = 2,
                        name = "Example 2",
                        nfcTagId = "123456789",
                        items = mutableListOf(),
                    ),
                    StorageSpace(
                        id = 3,
                        name = "Example 3",
                        nfcTagId = "123456789",
                        items = mutableListOf(),
                    ),
                ),
        )

    NFCWarehouseTheme {
        LocationScreen(locationMock)
    }
}
