package com.example.shoplist.domain

interface ShopListRepository {
    fun addShopItem(item: ShopItem)

    fun deleteShopItem(item: ShopItem)

    fun editShopItem(item: ShopItem)

    fun getShopItem(id: Int): ShopItem

    fun getShopList(): List<ShopItem>
}