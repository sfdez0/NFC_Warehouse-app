package com.github.sfdez0.nfcwarehouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.sfdez0.nfcwarehouse.ui.home.HomeRoute
import com.github.sfdez0.nfcwarehouse.ui.home.LocationRoute
import com.github.sfdez0.nfcwarehouse.ui.theme.NFCWarehouseTheme

/**
 * Main entry point of the application.
 * * This activity is responsible for setting up the UI content, applying the
 * [NFCWarehouseTheme], and initializing the navigation graph using a [NavHost].
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NFCWarehouseTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeRoute(
                            // Callback to navigate to the location screen with the given ID
                            onNavigateToLocation = { id ->
                                navController.navigate("location/$id")
                            },
                        )
                    }

                    composable(
                        route = "location/{id}",
                        arguments = listOf(navArgument("id") { type = NavType.LongType }),
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getLong("id")
                        LocationRoute(locationId = id ?: -1L)
                    }
                }
            }
        }
    }
}
