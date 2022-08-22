package com.example.shoplist.domain

class DeleteShopItemUseCase(private val shopItemRepository: ShopListRepository) {
    suspend fun deleteShopItem(item: ShopItem){
        shopItemRepository.deleteShopItem(item)
    }
}