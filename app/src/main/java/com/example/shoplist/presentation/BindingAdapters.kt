package com.example.shoplist.presentation

import androidx.databinding.BindingAdapter
import com.example.shoplist.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("ErrorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, error: Boolean){
    val message = if (error) textInputLayout.context.getString(R.string.error)
    else null
    textInputLayout.error = message
}

@BindingAdapter("ErrorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, error: Boolean){
    val message = if (error) textInputLayout.context.getString(R.string.error)
    else null
    textInputLayout.error = message
}

@BindingAdapter("CloseActivity")
fun bindCloseActivity(textInputLayout: TextInputLayout, error: Boolean){

}