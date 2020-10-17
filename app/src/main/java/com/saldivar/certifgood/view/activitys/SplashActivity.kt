package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saldivar.certifgood.R
import com.saldivar.permisolibrary.preferencesSaldivar

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        validarUser()
    }

    private fun validarUser() {
        val prefs = preferencesSaldivar(this@SplashActivity,0,"Datos_Usuario")
        val dato=prefs.getBoolean("actividad_user",false)
        if(dato){
            nextCertificacionesActivity()
        }else{
            nextLoginActivity()
        }
    }

    private fun nextLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }

    private fun nextCertificacionesActivity(){
        startActivity(Intent(this, CertificacionesActivity::class.java))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }
}