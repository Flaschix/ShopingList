package com.example.shoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    class ShopItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvCount = view.findViewById<TextView>(R.id.tvCount)
    }

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when(viewType){
            VIEW_TYPE_DISABLED -> R.layout.disable_item_shop
            VIEW_TYPE_ENABLED -> R.layout.enabled_shop_item
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.view.setOnLongClickListener{
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        var shopItem = shopList[position]
        return if(shopItem.enabled) {
            VIEW_TYPE_ENABLED
        } else{
            VIEW_TYPE_DISABLED
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    companion object{
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_POOL_SIZE = 10
    }
}