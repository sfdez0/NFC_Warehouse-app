package com.github.sfdez0.nfcwarehouse.data.model

/**
 * Class that represents an Item
 */
class Item(
    /**
     * Item ID
     */
    val id: Long,
    /**
     * Item name
     */
    val name: String,
    /**
     * Item quantity
     */
    val quantity: Long,
    /**
     * Storage Space ID where the Item is located
     */
    val storageSpaceId: Long,
)
