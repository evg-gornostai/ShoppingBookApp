package com.gornostai.shoppingbookapp.views.dialogs

import android.app.AlertDialog
import android.content.Context

object NewNoteDialog {

    fun showDialog(context: Context, listener: Listener){
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)


        dialog = builder.create()
        dialog.show()
    }

    interface Listener {
        fun onClick(title: String,description: String)
    }

}