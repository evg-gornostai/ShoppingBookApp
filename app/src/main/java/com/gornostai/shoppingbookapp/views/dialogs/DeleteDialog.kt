package com.gornostai.shoppingbookapp.views.dialogs

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.gornostai.shoppingbookapp.databinding.DialogDeleteBinding

object DeleteDialog {

    fun showDialog(context: Context,listener: Listener){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DialogDeleteBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.btnConfirm.setOnClickListener {
            listener.onClick()
            dialog?.dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener{
        fun onClick()
    }

}