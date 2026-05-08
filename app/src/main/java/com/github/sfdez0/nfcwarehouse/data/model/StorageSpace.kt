package com.github.sfdez0.nfcwarehouse.data.model

/**
 * Class that represents a Storage Space inside a Location
 */
class StorageSpace(
    /**
     * StorageSpace ID
     */
    val id: Long,
    /**
     * nfcTag ID
     */
    val nfcTagId: String,
    /**
     * StorageSpace name
     */
    var name: String,
    /**
     * List of StorageSpaces inside the Location
     */
    val items: MutableList<Item>,
)
