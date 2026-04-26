package com.github.sfdez0.nfcwarehouse.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sfdez0.nfcwarehouse.data.model.Location
import com.github.sfdez0.nfcwarehouse.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home Screen.
 */
class HomeViewModel : ViewModel() {
    /**
     * MutableStateFlow for the list of locations.
     */
    private val _locations = MutableStateFlow<List<Location>>(emptyList())

    /**
     * StateFlow exposing the list of locations.
     */
    val locations: StateFlow<List<Location>> = _locations

    /**
     * Initialize the ViewModel by fetching the location data.
     */
    init {
        getLocationData()
    }

    /**
     * Fetch the location data from the API.
     */
    private fun getLocationData() {
        viewModelScope.launch {
            try {
                val api = ApiService.create()
                _locations.value = api.getLocations()
            } catch (e: Exception) {
                Log.e("NFCW", "Error retrieving location data; API might be unreachable")
            }
        }
    }
}
