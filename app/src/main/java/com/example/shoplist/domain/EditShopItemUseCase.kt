package com.example.shoplist.domain

class EditShopItemUseCase(private val shopItemRepository: ShopListRepository) {
    suspend fun editShopItem(item: ShopItem){
        shopItemRepository.editShopItem(item)
    }
}