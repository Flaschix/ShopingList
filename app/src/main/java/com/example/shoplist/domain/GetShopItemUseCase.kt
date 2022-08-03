package com.example.shoplist.domain

class GetShopItemUseCase(private val shopItemRepository: ShopListRepository) {
    fun getShopItem(id: Int): ShopItem{
        return shopItemRepository.getShopItem(id)
    }
}