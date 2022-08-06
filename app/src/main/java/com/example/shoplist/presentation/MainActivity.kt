package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModelV
    private lateinit var adapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecycleView()

        viewModel = ViewModelProvider(this).get(MainViewModelV::class.java)
        viewModel.shopList.observe(this){
            adapter.shopList = it
        }
    }

    private fun setUpRecycleView(){
        val rvShopList = findViewById<RecyclerView>(R.id.rvShopList)
        adapter = ShopListAdapter()
        with(rvShopList){
            this.adapter = adapter
            this.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_POOL_SIZE)
            this.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.MAX_POOL_SIZE)
        }
    }
}