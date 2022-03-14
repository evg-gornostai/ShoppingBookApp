package com.gornostai.shoppingbookapp.utils

import android.content.Intent
import com.gornostai.shoppingbookapp.model.entities.ShoppingListItem

object ShareHelper {
    fun shareShopList(shopListItems: List<ShoppingListItem>,listName: String): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.apply {
            putExtra(Intent.EXTRA_TEXT, makeText(shopListItems,listName))
        }
        return intent
    }
    private fun makeText(shopListItems: List<ShoppingListItem>, listName: String): String{
        val result = StringBuilder()
        result.append(listName)
        result.append("\n")
        var counter = 1
        shopListItems.forEach {
            if (it.itemInfo.isNotEmpty()){
                result.append("${counter++} - ${it.name}\n     ${it.itemInfo}\n")
            } else {
                result.append("${counter++} - ${it.name}\n")
            }
        }
        return result.toString()
    }
}