package com.github.sfdez0.nfcwarehouse.ui.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.sfdez0.nfcwarehouse.R
import com.github.sfdez0.nfcwarehouse.ui.theme.NFCWarehouseTheme

/**
 * Stateful route for the Location Screen.
 * * This composable acts as a bridge between the [LocationViewModel] and the UI.
 * It collects the locations state flow and passes it down to the stateless [LocationScreen].
 *
 * @param viewModel The [LocationViewModel] responsible for managing the state of this screen.
 */
@Composable
fun LocationRoute(viewModel: LocationViewModel = viewModel()) {
    // TODO Collect info

    LocationScreen()
}

/**
 * Stateless composable for the Location Screen.
 * TODO Add description
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen() {
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
        Text(
            text = "ID",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize(),
        ) {
            // TODO Display location content
        }
    }
}

/**
 * Preview for the [LocationScreen] composable.
 */
@Preview(showBackground = true)
@Composable
fun LocationListPreview() {
    NFCWarehouseTheme {
        LocationScreen()
    }
}
