package com.saldivar.certifgood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.saldivar.certifgood.permissionsAndConexion.CheckConnectionPermissionsToPerformFunctionality
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(),CheckConnectionPermissionsToPerformFunctionality, View.OnClickListener {
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
        validarDatosUsurio()
    }

    private fun ui() {
        Siguientebtn.setOnClickListener(this@LoginActivity)
        progressBarImageView = findViewById(R.id.progressBarImageView)
    }

    private fun validarDatosUsurio() {
        ID.text
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.Siguientebtn->{
                checkConnectionAndPermission(this@LoginActivity)
                deleteColorERROR()
                captureDataEntered()
                validateCampos()
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

    private fun guardarCredentiales()=with(CredentialesLogin) {
        usuario = idName
        password = contrasenia
    }
}
