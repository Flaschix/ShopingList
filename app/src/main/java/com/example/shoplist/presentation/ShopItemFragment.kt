package com.example.shoplist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment: Fragment() {

    interface OnEditingFinishedListener{
        fun onEditingFinish()
    }

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var btnSave: Button
    private lateinit var ietName: EditText
    private lateinit var ietCount: EditText
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParam()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        } else throw RuntimeException("Activity must implement OnEditingFinishedListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.shop_item_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initView(view)
        initTexChangeListener()
        setRightScreenMode()
        observeViewModel()
    }

    private fun initView(view: View){
        ietName = view.findViewById(R.id.ietName)
        ietCount = view.findViewById(R.id.ietCount)
        tilName = view.findViewById(R.id.tilName)
        tilCount = view.findViewById(R.id.tilCount)
        btnSave = view.findViewById(R.id.btnSave)
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) getString(R.string.error)
            else null
            tilCount.error = message
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) getString(R.string.error)
            else null
            tilName.error = message
        }

        viewModel.closeActivity.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinish()
        }
    }
//
    private fun setRightScreenMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }
//
    private fun initTexChangeListener() {
        ietName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        ietCount.addTextChangedListener(object : TextWatcher {
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
        private const val SCREEN_MODE = "extra_mode"
        private const val SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }

        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }

    private fun parseParam(){
        val args = requireArguments()
        if(!args.containsKey(SCREEN_MODE)) throw RuntimeException("Intent don't have mode")
        val mode = args.getString(SCREEN_MODE)
        if(mode != MODE_ADD && mode != MODE_EDIT) throw RuntimeException("Mode is not found")

        screenMode = mode

        if(screenMode == MODE_EDIT){
            if(!args.containsKey(SHOP_ITEM_ID)) throw RuntimeException("Intent don't have shop_item_id")
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }
//
    private fun launchEditMode(){
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner){
            ietName.setText(it.name)
            ietCount.setText(it.count.toString())
        }

        btnSave.setOnClickListener{
            viewModel.editShopItem( name = ietName.text.toString(), count = ietCount.text.toString())
        }
    }
    private fun launchAddMode(){
        btnSave.setOnClickListener{
            viewModel.addShopItem(name = ietName.text.toString(), count = ietCount.text.toString())
        }
    }
}