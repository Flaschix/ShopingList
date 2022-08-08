package com.example.shoplist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModelV
    private lateinit var shopListAdapter: ShopListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecycleView()

        viewModel = ViewModelProvider(this).get(MainViewModelV::class.java)
        viewModel.shopList.observe(this){
            shopListAdapter.submitList(it)
        }

        binding.flaAddItem.setOnClickListener{
            val intent = ShopItemActivity.newIntentAddShopItem(this)
            startActivity(intent)
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

        setUpLongClickListener()
        setUpClickListener()
        setUpSwipeListener(rvShopList)
    }

    private fun setUpSwipeListener(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setUpClickListener() {
        shopListAdapter.onShopItemClickListener = {
            val intent = ShopItemActivity.newIntentEditShopItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setUpLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}