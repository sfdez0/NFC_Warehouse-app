package com.github.sfdez0.nfcwarehouse.data.model

/**
 * Class that represents an Item
 */
class Item(
    /**
     * Item ID
     */
    val id: Long? = null,
    /**
     * Item name
     */
    val name: String? = null,
    /**
     * Item quantity
     */
    val quantity: Int? = null,
    /**
     * Storage Space ID where the Item is located
     */
    val storageSpaceId: Long? = null,
)
