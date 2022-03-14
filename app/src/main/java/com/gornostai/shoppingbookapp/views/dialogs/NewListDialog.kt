package com.gornostai.shoppingbookapp.views.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.gornostai.shoppingbookapp.R
import com.gornostai.shoppingbookapp.databinding.DialogNewListBinding

object NewListDialog {

    fun showDialog(context: Context, listener: Listener, name: String = ""){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DialogNewListBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.edtNewListName.setText(name)
        if (name.isNotEmpty()){
            binding.btnCreate.setText(R.string.update)
        }
        binding.btnCreate.setOnClickListener {
            val listName = binding.edtNewListName.text.toString()
            if (listName.isNotEmpty()){
                listener.onClick(listName)
            }
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener {
        fun onClick(name: String)
    }

}