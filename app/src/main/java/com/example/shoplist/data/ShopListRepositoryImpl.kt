package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl: ShopListRepository {
    val list: MutableList<ShopItem> = ArrayList()
    private var autoIncrementId = 0

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) item.id = autoIncrementId
        list.add(item)
        autoIncrementId++
    }

    override fun deleteShopItem(item: ShopItem) {
        list.remove(item)
        autoIncrementId--
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id)
        deleteShopItem(oldItem)
        addShopItem(item)
    }

    override fun getShopItem(id: Int): ShopItem {
        return list.find { it.id == id } ?: throw RuntimeException("Element whit id $id not found")
    }

    override fun getShopList(): List<ShopItem> {
        return list.toMutableList()
    }

}