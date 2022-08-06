package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.ShopListRepository
import java.lang.RuntimeException
import java.util.*

object ShopListRepositoryImpl: ShopListRepository {
    private val shopListLiveData = MutableLiveData<List<ShopItem>>()

    private val list = sortedSetOf<ShopItem>({item1, item2 -> item1.id.compareTo(item2.id)})

    private var autoIncrementId = 0

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) item.id = autoIncrementId
        list.add(item)
        autoIncrementId++
        updateLiveData()
    }

    override fun deleteShopItem(item: ShopItem) {
        list.remove(item)
        updateLiveData()
    }

    override fun editShopItem(item: ShopItem) {
        val oldItem = getShopItem(item.id)
        deleteShopItem(oldItem)
        addShopItem(item)
        updateLiveData()
    }

    override fun getShopItem(id: Int): ShopItem {
        return list.find { it.id == id } ?: throw RuntimeException("Element whit id $id not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    private fun updateLiveData(){
        shopListLiveData.value = list.toList()
    }

}