package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class Mapper {
    fun mapShopItemToShopItemDbModel(shopItem: ShopItem) = ShopItemDbModel(
        shopItem.id,
        shopItem.name,
        shopItem.count,
        shopItem.enabled
    )

    fun mapShopItemDbModelToShopItem(shopItemDbModel: ShopItemDbModel) = ShopItem(
        shopItemDbModel.id,
        shopItemDbModel.name,
        shopItemDbModel.count,
        shopItemDbModel.enabled
    )

    fun mapListShopItemDbModelToListShopItem(list: List<ShopItemDbModel>) = list.map {
        mapShopItemDbModelToShopItem(it)
    }
}