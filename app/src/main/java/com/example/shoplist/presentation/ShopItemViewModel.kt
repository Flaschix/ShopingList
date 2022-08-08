package com.example.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _closeActivity = MutableLiveData<Unit>()
    val closeActivity: LiveData<Unit>
        get() = _closeActivity

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    fun getShopItem(itemId: Int){
        val shopItem = getShopItemUseCase.getShopItem(itemId)
        _shopItem.value = shopItem
    }

    fun addShopItem(name: String?, count: String?){
        val itemName = parseName(name)
        val itemCount = parseCount(count)
        val fieldsValid = validInput(itemName, itemCount)
        if(fieldsValid) {
            addShopItemUseCase.addShopItem(ShopItem(name = itemName, count = itemCount))
            finishWork()
        }

    }

    fun editShopItem(name: String?, count: String?){
        val itemName = parseName(name)
        val itemCount = parseCount(count)
        val fieldsValid = validInput(itemName, itemCount)
        if(fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = itemName, count = itemCount)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
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
        if(name.isBlank()) {
            _errorInputName.value = true
            return false
        }
        if(count <= 0) {
            _errorInputCount.value = true
            return false
        }
        return true
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _closeActivity.value = Unit
    }

}