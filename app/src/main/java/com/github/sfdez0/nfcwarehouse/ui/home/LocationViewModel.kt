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
 * ViewModel for the Location Screen.
 */
class LocationViewModel : ViewModel() {
    /**
     * MutableStateFlow for the location.
     */
    private val _location = MutableStateFlow<Location?>(null)

    /**
     * StateFlow exposing the location.
     */
    val location: StateFlow<Location?> = _location

    /**
     * Fetch the location data from the API.
     */
    fun getLocationData(id: Long) {
        viewModelScope.launch {
            try {
                val api = ApiService.create()
                _location.value = api.getLocationById(id)
            } catch (e: Exception) {
                Log.e("NFCW", "Error retrieving location data; API might be unreachable")
            }
        }
    }
}
