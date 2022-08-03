package com.example.shoplist.domain

class GetShopListUseCase(private val shopItemRepository: ShopListRepository) {
    fun getShopList(): List<ShopItem>{
        return shopItemRepository.getShopList()
    }
}