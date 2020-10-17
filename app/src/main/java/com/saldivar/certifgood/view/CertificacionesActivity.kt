package com.saldivar.certifgood.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.CredentialesLogin
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.permisolibrary.preferencesSaldivar

class CertificacionesActivity : AppCompatActivity() {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificaciones)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        ShowDialog.dialogShowOptions("¿Desea cerrar la sesion?",this@CertificacionesActivity)
    }

    fun desactivarActividadUser(){
        viewModel.updateActividadUsuario(CredentialesLogin.id_documento,false).observe(this,
            Observer {
                when(it){
                    true->{
                        val prefs = preferencesSaldivar(this@CertificacionesActivity,0,"Datos_Usuario")
                        val pref = prefs.edit()
                        pref.putBoolean("actividad_user",false)
                        pref.apply()
                        backLoginActivity()}
                    false->ShowDialog.dialogShow(
                        "Ocurrio un error inesperado",
                        this@CertificacionesActivity
                    )
                }
            })
    }

     private fun backLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.right_in, R.anim.right_out)
        finish()
    }
}