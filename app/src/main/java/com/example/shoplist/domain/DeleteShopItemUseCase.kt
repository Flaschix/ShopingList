package com.example.shoplist.domain

class DeleteShopItemUseCase(private val shopItemRepository: ShopListRepository) {
    fun deleteShopItem(item: ShopItem){
        shopItemRepository.deleteShopItem(item)
    }
}