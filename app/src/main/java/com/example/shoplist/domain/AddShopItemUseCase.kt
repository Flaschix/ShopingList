package com.example.shoplist.domain

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun addShopItem(item: ShopItem){
        shopListRepository.addShopItem(item)
    }
}