package com.example.shoplist.domain

data class ShopItem(var id: Int = UNDEFINED_ID, var name: String, var count: Int, var enabled: Boolean = true){

    companion object{
        const val UNDEFINED_ID = 0
    }
}
