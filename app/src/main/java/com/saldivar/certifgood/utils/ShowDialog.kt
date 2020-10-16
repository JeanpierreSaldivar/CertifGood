package com.saldivar.certifgood.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.saldivar.certifgood.R

object ShowDialog {

    private lateinit var dialogView:View

    fun dialogShow(message: String, context: Context) {

        dialogView = LayoutInflater.from(context).
        inflate(
            R.layout.alert_error_conexion_internet,
          null )

        showDialogConnection(message, dialogView,context)
    }

    private fun showDialogConnection(message: String, mDialogView: View, context: Context){
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        val  mAlertDialog = mBuilder.create()
        val optionAccept =  mDialogView.findViewById(R.id.boton_aceptar) as Button
        mAlertDialog.show()
        mAlertDialog.window?.setBackgroundDrawable(null)
        val body = mDialogView.findViewById(R.id.texto_alert) as TextView
        body.text = message
        optionAccept.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }

}