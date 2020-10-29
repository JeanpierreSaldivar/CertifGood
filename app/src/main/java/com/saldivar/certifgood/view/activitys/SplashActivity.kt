package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.CredentialsLogin
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.permisolibrary.goneModalProgressSaldivar
import com.saldivar.permisolibrary.preferencesSaldivar

class SplashActivity : AppCompatActivity() {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        validarUser()
    }

    private fun validarUser() {
        val prefs = preferencesSaldivar(this,0,"Datos_Usuario")
        CredentialsLogin.actividad_user= prefs.getBoolean("actividad_user", false)
        CredentialsLogin.usuario= prefs.getString("usuario", "")!!
        CredentialsLogin.password= prefs.getString("contraseña", "")!!
        viewModel.getResultadoBusquedaUsuario().observe(this, Observer {
            when(it){
                "Usuario existe"->{
                    viewModel.getActividadUsuarioEncontrado().observe(this, Observer {actividad->
                        when(actividad){
                            "Usuario activo"-> nextCertificacionesActivity()
                            else->{
                                nextLoginActivity()
                            }
                        }
                    })
                }
                else-> nextLoginActivity()
            }
        })
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