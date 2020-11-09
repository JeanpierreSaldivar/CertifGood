package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.saldivar.certifgood.R
import com.saldivar.certifgood.repo.conexionFirebase
import com.saldivar.certifgood.utils.*
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SplashActivity : AppCompatActivity() {
    private val prefs by lazy { this.preferences() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        //descomentar para ambiente de desarrollo
        //FirebaseApp.initializeApp(this, conexionFirebase.optionsDesarrollo,"databaseDesa")
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        actividadUser()
    }

    private fun actividadUser() {
        when(prefs.getString(getString(R.string.activo_User), "")){
            getString(R.string.activo_User)->nextCertificacionesActivity()
            else->nextLoginActivity()
        }
    }

    private fun nextLoginActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }

    private fun nextCertificacionesActivity(){
        startActivity(Intent(this, CertificationsActivity::class.java))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }
}