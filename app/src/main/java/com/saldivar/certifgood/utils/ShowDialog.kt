package com.saldivar.certifgood.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.view.activitys.CertificationsActivity
import com.saldivar.certifgood.view.activitys.QuestionsActivity
import com.saldivar.certifgood.view.fragments.ChronometerFragment
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_chronometer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object ShowDialog {

    private lateinit var dialogView:View

    fun dialogShowCalificacionBuena(message: String, context: Context) {

        dialogView = LayoutInflater.from(context).
        inflate(
            R.layout.alert_calificacion_buena,
            null )

        showDialogConnection(message, dialogView,context)
    }

    fun dialogShowCalificacionMala(message: String, context: Context) {

        dialogView = LayoutInflater.from(context).
        inflate(
            R.layout.alert_calificacion_mala,
            null )

        showDialogConnection(message, dialogView,context)
    }

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
        val mAlertDialog = mBuilder.create()
        val optionAccept = mDialogView.findViewById(R.id.boton_aceptar) as Button
        mAlertDialog.show()
        mAlertDialog.window?.setBackgroundDrawable(null)
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.setCancelable(false)
        val body = mDialogView.findViewById(R.id.texto_alert) as TextView
        body.text = message
        when (context) {
            is QuestionsActivity -> {
                optionAccept.setOnClickListener {
                    if(message=="Compruebe su conexion a internet"){
                        mAlertDialog.dismiss()
                    }else{
                        CoroutineScope(Dispatchers.IO).launch{
                            QuestionObject.listQuestion!!.clear()
                            QuestionObject.nota =0
                            QuestionObject.listQuestionSize = 0
                            QuestionObject.contador_pregunta =0
                            mAlertDialog.dismiss()
                            context.backActivity(context)

                        }
                    }
                }
            }
            else -> {
                optionAccept.setOnClickListener{
                    mAlertDialog.dismiss()
                }
            }
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
                is QuestionsActivity->{
                    if(message=="Compruebe su conexion a internet"){
                        mAlertDialog.dismiss()
                    }else{
                        CoroutineScope(Dispatchers.IO).launch{
                            QuestionObject.listQuestion!!.clear()
                            QuestionObject.nota =0
                            QuestionObject.listQuestionSize = 0
                            QuestionObject.contador_pregunta =0
                            mAlertDialog.dismiss()
                            context.backActivity(context)
                        }
                    }
                }
                is CertificationsActivity -> {
                    val prefs = preferencesSaldivar(context,0,"Datos_Usuario")
                    if(prefs.getString("contraseña", "") == "defecto" ){
                        val pref2 = prefs.edit()
                        pref2.clear()
                        pref2.apply()
                        val ss = CertificationsActivity()
                        ss.backLoginActivity(context)
                    }
                    else{
                        val pref = prefs.edit()
                        pref.putBoolean("actividad_user",false)
                        pref.apply()
                        ViewModelProvider(context).get(MainViewModel::class.java).updateActividadUsuario(prefs.getString("id_documento",CredentialsLogin.id_documento)!!,false).observe(context,
                            Observer {
                                when(it){
                                    true->{
                                        val pref2 = prefs.edit()
                                        pref2.clear()
                                        pref2.apply()
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

                }
                else -> mAlertDialog.dismiss()
            }

        }
        optionCancelar.setOnClickListener {
            mAlertDialog.dismiss()
            SwitchFragment.detenerChronometer= 0}
    }
}