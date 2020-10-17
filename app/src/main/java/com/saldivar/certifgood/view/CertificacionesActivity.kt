package com.saldivar.certifgood.view

import android.content.Context
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificaciones)
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        ShowDialog.dialogShowOptions("¿Desea cerrar la sesion?",this@CertificacionesActivity)
    }



     internal fun backLoginActivity(context: CertificacionesActivity) {
         context.apply {
             startActivity(Intent(context, LoginActivity::class.java))
             overridePendingTransition(R.anim.right_in, R.anim.right_out)
             finish()
         }

    }
}