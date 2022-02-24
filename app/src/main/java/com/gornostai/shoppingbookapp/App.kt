package com.gornostai.shoppingbookapp

import android.app.Application
import com.gornostai.shoppingbookapp.model.db.MainDataBase

class App: Application() {
    val dataBase by lazy { MainDataBase.getDataBase(this) }
}