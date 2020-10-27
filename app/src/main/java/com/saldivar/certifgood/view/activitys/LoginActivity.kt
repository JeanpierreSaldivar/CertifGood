package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.utils.CredentialsLogin
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.utils.permissionsAndConexion.CheckConnectionPermissionsToPerformFunctionality
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.permisolibrary.goneModalProgressSaldivar
import com.saldivar.permisolibrary.preferencesSaldivar
import com.saldivar.permisolibrary.showModalProgressSaldivar
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(),CheckConnectionPermissionsToPerformFunctionality, View.OnClickListener {
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    private lateinit var idName: String
    private lateinit var contrasenia: String
    private lateinit var progressBarImageView: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        checkConnectionAndPermission(this@LoginActivity)
        ui()
    }

    private fun ui() {
        Siguientebtn.setOnClickListener(this@LoginActivity)
        progressBarImageView = findViewById(R.id.progressBarImageView)
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.Siguientebtn ->{
                if(CheckInternetConnection.validateInternetConnection(this@LoginActivity)){
                    deleteColorERROR()
                    captureDataEntered()
                    validateCampos()
                }
                else{
                    ShowDialog.dialogShow("Compruebe su conexion a internet", this@LoginActivity)
                }
            }
        }

    }
    private fun deleteColorERROR() {
        setOf(USER_et,Password_et).forEach { it.isErrorEnabled=false}
    }

    private fun captureDataEntered() {
        idName = ID.text.toString()
        contrasenia = password.text.toString()
    }

    private fun validateCampos() {
        when {
            idName.isEmpty() && contrasenia.isEmpty() -> USER_et.error = "Ingrese el nombre de usuario"
            idName.isEmpty() -> USER_et.error = "Ingrese el nombre de usuario"
            contrasenia.isEmpty() -> Password_et.error = "Ingresa la contraseña"
            else->guardarCredentiales()
        }
    }

    private fun guardarCredentiales()=with(CredentialsLogin) {
        usuario = idName
        password = contrasenia
        if(CheckInternetConnection.validateInternetConnection(this@LoginActivity)){
            buscarUsuarioFirebase()
        }
        else{
            ShowDialog.dialogShow("Compruebe su conexion a internet", this@LoginActivity)
        }
    }

    private fun buscarUsuarioFirebase() {
        showModalProgressSaldivar(progressBarImageView)
        viewModel.getResultadoBusquedaUsuario().observe(this, Observer {
            goneModalProgressSaldivar(progressBarImageView)
            when(it){
                "Usuario existe"->{
                    viewModel.getActividadUsuarioEncontrado().observe(this, Observer {actividad->
                        when(actividad){
                            "Usuario activo"-> ShowDialog.dialogShow(
                                "Ya hay un usuario logueado con esta cuenta",
                                this@LoginActivity
                            )
                            else->{
                                viewModel.updateActividadUsuario(CredentialsLogin.id_documento,true).observe(this,
                                    Observer { actualizacion->
                                        when(actualizacion){
                                            true->{
                                                CredentialsLogin.actividad_user=true
                                                val prefs = preferencesSaldivar(this,0,"Datos_Usuario")
                                                val pref =prefs.edit()
                                                pref.putString("usuario",CredentialsLogin.usuario)
                                                pref.putString("contraseña",CredentialsLogin.password)
                                                pref.putString("id_documento",CredentialsLogin.id_documento)
                                                pref.putBoolean("actividad_user",CredentialsLogin.actividad_user)
                                                pref.apply()
                                                nextActivity()}
                                            false-> ShowDialog.dialogShow(
                                                "Ocurrio un error inesperado",
                                                this@LoginActivity
                                            )
                                        }
                                    })
                            }
                        }
                    })
                }
                else-> ShowDialog.dialogShow("Usuario o contraseña incorrecta", this@LoginActivity)
            }
        })
    }

    private fun nextActivity(){
        startActivity(Intent(this, CertificationsActivity::class.java))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }
}
