package com.example.shoplist.domain

class EditShopItemUseCase(private val shopItemRepository: ShopListRepository) {
    fun editShopItem(item: ShopItem){
        shopItemRepository.editShopItem(item)
    }
}