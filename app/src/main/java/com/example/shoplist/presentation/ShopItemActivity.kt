package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityShopItemBinding
import com.example.shoplist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        parseIntent()

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        initTexChangeListener()

        setRightScreenMode()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(this) {
            val message = if (it) getString(R.string.error)
            else null
            binding.tilCount.error = message
        }

        viewModel.errorInputName.observe(this) {
            val message = if (it) getString(R.string.error)
            else null
            binding.tilName.error = message
        }

        viewModel.closeActivity.observe(this) {
            finish()
        }
    }

    private fun setRightScreenMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun initTexChangeListener() {
        binding.ietName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.ietCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    companion object{
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddShopItem(context: Context): Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditShopItem(context: Context, shopItemId: Int): Intent{
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_SCREEN_MODE)) throw RuntimeException("Intent don't have mode")
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode != MODE_ADD && mode != MODE_EDIT) throw RuntimeException("Mode is not found")
        screenMode = mode


        if(screenMode == MODE_EDIT){
            if(!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) throw RuntimeException("Intent don't have shop_item_id")
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this){
            binding.ietName.setText(it.name)
            binding.ietCount.setText(it.count.toString())
        }

        binding.btnSave.setOnClickListener{
            viewModel.editShopItem(name = binding.ietName.text.toString(), count = binding.ietCount.text.toString())
        }
    }
    private fun launchAddMode(){
        binding.btnSave.setOnClickListener{
            viewModel.addShopItem(name = binding.ietName.text.toString(), count = binding.ietCount.text.toString())
        }
    }
}