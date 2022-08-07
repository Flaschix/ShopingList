package com.example.shoplist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.AddShopItemUseCase
import com.example.shoplist.domain.EditShopItemUseCase
import com.example.shoplist.domain.GetShopItemUseCase
import com.example.shoplist.domain.ShopItem
import java.lang.Exception

class ShopItemViewModel: ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getShopItem(itemId: Int){
        val shopItem = getShopItemUseCase.getShopItem(itemId)
    }

    fun addShopItem(name: String?, count: String?){
        val itemName = parseName(name)
        val itemCount = parseCount(count)
        val fieldsValid = validInput(itemName, itemCount)
        if(fieldsValid) addShopItemUseCase.addShopItem(ShopItem(name = itemName, count = itemCount))
    }

    fun editShopItem(name: String?, count: String?){
        val itemName = parseName(name)
        val itemCount = parseCount(count)
        val fieldsValid = validInput(itemName, itemCount)
        if(fieldsValid) editShopItemUseCase.editShopItem(ShopItem(name = itemName, count = itemCount))
    }

    private fun parseName(name: String?): String{
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int{
        return try {
            count?.toInt() ?: 0
        } catch (e: Exception) { 0 }
    }

    private fun validInput(name: String, count: Int): Boolean{
        if(name.isBlank()) return false
        if(count <= 0) return false
        return true
    }
}