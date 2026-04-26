package com.github.sfdez0.nfcwarehouse.data.model

/**
 * Class that represents a Location
 */
class Location(
    /**
     * Location ID
     */
    val id: Long,
    /**
     * Location name
     */
    var name: String,
    /**
     * Location description
     */
    var description: String? = null,
    /**
     * List of StorageSpaces inside the Location
     */
    val storageSpaces: MutableList<StorageSpaces>,
)
