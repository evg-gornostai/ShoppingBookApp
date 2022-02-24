package com.gornostai.shoppingbookapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gornostai.shoppingbookapp.MainViewModel
import com.gornostai.shoppingbookapp.model.db.MainDataBase

class ViewModelFactory(val dataBase: MainDataBase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dataBase) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}