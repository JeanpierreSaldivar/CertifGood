package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.*
import com.saldivar.certifgood.utils.permissionsAndConexion.CheckConnectionPermissionsToPerformFunctionality
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(),CheckConnectionPermissionsToPerformFunctionality, View.OnClickListener {
    private val viewModel by lazy{ this.viewModel()}
    private lateinit var task : Task<GoogleSignInAccount>
    private  val googleSignIn by lazy { 100 }
    private val prefs by lazy { this.preferences() }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        checkConnectionAndPermission(this@LoginActivity)
        ui()
    }

    private fun ui() {
        Googlebtn.setOnClickListener(this@LoginActivity)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.Googlebtn ->{
                if(CheckInternetConnection.validateInternetConnection(this@LoginActivity)) {
                val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
                val googleClient = GoogleSignIn.getClient(this,googleConf)
                googleClient.signOut()
                startActivityForResult(googleClient.signInIntent, googleSignIn)}
                else{ dialogShow(getString(R.string.text_error_conexion_internet)) }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == googleSignIn && resultCode == -1){
            task =GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            try {
                if(account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){ queryUserBdFirebase(account) }
                        else{ dialogShow(getString(R.string.usuario_no_existe)) }
                    }
                }
            }catch (e:ApiException){
                dialogShow(getString(R.string.error_inesperado))
            }
        }
    }

    private fun queryUserBdFirebase(account: GoogleSignInAccount) {
        viewModel.getResultadoBusquedaUsuario(account.email!!).observe(this, Observer {queryUser ->
            when(queryUser){
                getString(R.string.usuario_existe)->{savePrefUser(account) }
                else->{ saveUserBdFirebase(account) }
            }
        })
    }

    private fun saveUserBdFirebase(account: GoogleSignInAccount) {
        viewModel.saveGoogleUser(account.email!!,account.photoUrl.toString(),
            account.displayName!!).observe(this, Observer {saveUser->
            if(saveUser){ savePrefUser(account) }
            else{ dialogShow(message = getString(R.string.error_inesperado)) }
        })
    }

    private fun savePrefUser(account: GoogleSignInAccount){
        prefs.edit().apply {
            putString(getString(R.string.foto_User),account.photoUrl.toString())
            putString(getString(R.string.name_User),account.displayName)
            putString(getString(R.string.email_User),account.email!!)
            putString(getString(R.string.activo_User),getString(R.string.activo_User))
            apply()
        }
        nextActivity()
    }
    private fun dialogShow(message:String){
        ShowDialog.dialogShow(message = message, context = this@LoginActivity)
    }
    private fun nextActivity(){
        startActivity(Intent(this, CertificationsActivity::class.java))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }
}
