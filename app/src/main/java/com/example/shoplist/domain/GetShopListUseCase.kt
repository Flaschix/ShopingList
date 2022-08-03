package com.example.shoplist.domain

import androidx.lifecycle.LiveData

class GetShopListUseCase(private val shopItemRepository: ShopListRepository) {
    fun getShopList(): LiveData<List<ShopItem>>{
        return shopItemRepository.getShopList()
    }
}