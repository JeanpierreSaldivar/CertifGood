package com.saldivar.certifgood.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.view.activitys.CertificationsActivity
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.permisolibrary.preferencesSaldivar

object ShowDialog {

    private lateinit var dialogView:View

    fun dialogShow(message: String, context: Context) {

        dialogView = LayoutInflater.from(context).
        inflate(
            R.layout.alert_error_conexion_internet,
          null )

        showDialogConnection(message, dialogView,context)
    }

    fun dialogShowOptions(message: String, context: Context) {

        dialogView = LayoutInflater.from(context).
        inflate(
            R.layout.alert_advertencia_dos_opciones,
            null )

        showDialogOptions(message, dialogView,context)
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

    private fun showDialogOptions(message: String, mDialogView: View, context: Context){
        val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
        val  mAlertDialog = mBuilder.create()
        val optionAccept =  mDialogView.findViewById(R.id.boton_aceptar) as Button
        val optionCancelar =  mDialogView.findViewById(R.id.boton_cancelar) as Button
        mAlertDialog.show()
        mAlertDialog.window?.setBackgroundDrawable(null)
        val body = mDialogView.findViewById(R.id.texto_alert) as TextView
        body.text = message
        optionAccept.setOnClickListener{
            when (context) {
                is CertificationsActivity -> {
                    val prefs = preferencesSaldivar(context,0,"Datos_Usuario")
                    val pref = prefs.edit()
                    ViewModelProvider(context).get(MainViewModel::class.java).updateActividadUsuario(prefs.getString("id_documento",CredentialsLogin.id_documento)!!,false).observe(context,
                        Observer {
                            when(it){
                                true->{
                                    pref.putBoolean("actividad_user",false)
                                    pref.apply()
                                    val ss = CertificationsActivity()
                                    ss.backLoginActivity(context)
                                }
                                false-> dialogShow(
                                    "Ocurrio un error inesperado",
                                    context
                                )
                            }
                        })
                }
                else -> mAlertDialog.dismiss()
            }

        }
        optionCancelar.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}