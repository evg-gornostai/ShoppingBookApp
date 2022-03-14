package com.gornostai.shoppingbookapp.views.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.gornostai.shoppingbookapp.databinding.DialogEditListItemBinding
import com.gornostai.shoppingbookapp.model.entities.ShoppingListItem

object EditListItemDialog {

    fun showDialog(context: Context, item: ShoppingListItem, listener: Listener){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DialogEditListItemBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
            binding.apply {
                edName.setText(item.name)
                edInfo.setText(item.itemInfo)
                btnUpdate.setOnClickListener {
                    if (edName.text.toString().isNotEmpty()){
                        listener.onClick(item.copy(name = edName.text.toString(), itemInfo = edInfo.text.toString()))
                    }
                    dialog?.dismiss()
                }
            }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener {
        fun onClick(item: ShoppingListItem)
    }

}