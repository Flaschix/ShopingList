package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModelV
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecycleView()

        viewModel = ViewModelProvider(this).get(MainViewModelV::class.java)
        viewModel.shopList.observe(this){
            shopListAdapter.shopList = it
        }
    }

    private fun setUpRecycleView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rvShopList)
        with(rvShopList){
            shopListAdapter = ShopListAdapter()
            this.adapter = shopListAdapter
            this.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_POOL_SIZE)
            this.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.MAX_POOL_SIZE)
        }
    }
}