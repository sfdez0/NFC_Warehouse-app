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
import com.github.sfdez0.nfcwarehouse.data.model.Item
import com.github.sfdez0.nfcwarehouse.data.model.StorageSpace
import com.github.sfdez0.nfcwarehouse.ui.theme.NFCWarehouseTheme

/**
 * Stateful route for the StorageSpace Screen.
 * * This composable acts as a bridge between the [StorageSpaceViewModel] and the UI.
 * It collects the storage spaces state flow and passes it down to the stateless [StorageSpaceScreen].
 *
 * @param storageSpaceId The ID of the storage space to display
 * @param viewModel The [StorageSpaceViewModel] responsible for managing the state of this screen.
 */
@Composable
fun StorageSpaceRoute(
    storageSpaceId: Long,
    viewModel: StorageSpaceViewModel = viewModel(),
) {
    LaunchedEffect(key1 = storageSpaceId) {
        viewModel.getStorageSpaceData(storageSpaceId)
    }

    val storageSpace by viewModel.storageSpace.collectAsState()

    StorageSpaceScreen(storageSpace)
}

/**
 * Stateless composable for the StorageSpace Screen.
 * This screen displays the storage space UI, including a top app bar and
 * all the information about the storage space.
 *
 * @param storageSpace The [StorageSpace] to display
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageSpaceScreen(storageSpace: StorageSpace?) {
    if (storageSpace == null) {
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
                    onClick = { }, // TODO Add item screen
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                        contentDescription = "Add item",
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
                    text = storageSpace.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ID: ${storageSpace.id}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = storageSpace.nfcTagId,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = storageSpace.items,
                        // Use the ID of each storage space as the key
                        key = { item -> item.id },
                    ) { item ->
                        ItemListItem(item)
                    }
                }
            }
        }
    }
}

/**
 * Composable for a single [Item] in the list.
 *
 * @param item The [Item] to display
 */
@Composable
fun ItemListItem(item: Item) {
    // State to track if the item is expanded or not
    var expanded by rememberSaveable { mutableStateOf(false) }

    // Card for the item
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
            // Name of the item
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // ID
            Text(
                text = "ID: ${item.id}",
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Quantity
            Text(
                text = "NFC Tag: ${item.quantity}",
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
 * Preview for the [StorageSpaceScreen] composable.
 */
@Preview(showBackground = true)
@Composable
fun StorageSpacePreview() {
    val storageSpaceMock =
        StorageSpace(
            id = 1,
            nfcTagId = "abcd1234",
            name = "StorageSpace 1",
            items =
                mutableListOf(
                    Item(
                        id = 1,
                        name = "Item 1",
                        quantity = 1,
                        storageSpaceId = 1,
                    ),
                    Item(
                        id = 2,
                        name = "Item 2",
                        quantity = 1,
                        storageSpaceId = 1,
                    ),
                ),
        )

    NFCWarehouseTheme {
        StorageSpaceScreen(storageSpaceMock)
    }
}
