package com.github.sfdez0.nfcwarehouse.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.sfdez0.nfcwarehouse.data.model.StorageSpace
import com.github.sfdez0.nfcwarehouse.data.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the StorageSpace Screen.
 */
class StorageSpaceViewModel : ViewModel() {
    /**
     * MutableStateFlow for the storage space.
     */
    private val _storageSpace = MutableStateFlow<StorageSpace?>(null)

    /**
     * StateFlow exposing the storage space.
     */
    val storageSpace: StateFlow<StorageSpace?> = _storageSpace

    /**
     * Fetch the storage space data from the API.
     */
    fun getStorageSpaceData(id: Long) {
        viewModelScope.launch {
            try {
                val api = ApiService.create()
                _storageSpace.value = api.getStorageSpaceById(id)
            } catch (e: Exception) {
                Log.e("NFCW", "Error retrieving storage space data; API might be unreachable")
            }
        }
    }
}
