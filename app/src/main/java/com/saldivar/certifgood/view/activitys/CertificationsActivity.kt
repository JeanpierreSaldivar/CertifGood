package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.*
import com.saldivar.certifgood.view.fragments.HistorialFragment
import com.saldivar.certifgood.view.fragments.ListCertificationsFragment
import com.saldivar.certifgood.viewModel.MainViewModel
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection
import kotlinx.android.synthetic.main.activity_certificaciones.*

class CertificationsActivity : AppCompatActivity(), View.OnClickListener{
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_CertificacionesActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificaciones)
        supportActionBar?.hide()
        ui()
        openFragment(ListCertificationsFragment.newInstance())
        activarFloatingButton()

    }

    private fun activarFloatingButton() {
        val prefs = preferencesSaldivar(this,0,"Datos_Usuario")
        val user = prefs.getString("usuario", CredentialsLogin.usuario)!!
        viewModel.sizeHistorial(user).observeForever {
            if(it!=0){
                floatingButtonHistorial.visibility= View.VISIBLE
            }
        }
    }

    private fun ui() {
        val prefs = preferencesSaldivar(this,0,"Datos_Usuario")
        textHolaUser.text =prefs.getString("name_User","Usuario")
        imagen_user.loadByUrlPicaso( prefs.getString("foto","no foto")!!,R.drawable.ic_usuario_defecto)
        back_flecha.setOnClickListener(this@CertificationsActivity)
        floatingButtonHistorial.setOnClickListener(this@CertificationsActivity)
    }

    private fun openFragment(fragment: ListCertificationsFragment){
      supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container_fragment_certificaciones,fragment)
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    addToBackStack(null)
                    commit()}
    }

    override fun onBackPressed() {
        consultarFragmentMostreado()
    }

    private fun consultarFragmentMostreado() {
        if(CheckInternetConnection.validateInternetConnection(this@CertificationsActivity)) {
            if (SwitchFragment.numeroFragmentMostrado == 2) {
                floatingButtonHistorial.visibility = View.VISIBLE
                openFragment(ListCertificationsFragment.newInstance())
            } else {
                ShowDialog.dialogShowOptions(
                    "¿Desea cerrar la sesión?",
                    this@CertificationsActivity
                )
            }
        }else{
            ShowDialog.dialogShow("Compruebe su conexión a internet", this@CertificationsActivity)
        }
    }


    internal fun backLoginActivity(context: CertificationsActivity) {
         context.apply {
             startActivity(Intent(context, LoginActivity::class.java))
             overridePendingTransition(R.anim.right_in, R.anim.right_out)
             finish()
         }

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.back_flecha->{ consultarFragmentMostreado() }
            R.id.floatingButtonHistorial-> openFragmentHistorial(HistorialFragment.newInstance())
        }
    }

    private fun openFragmentHistorial(fragment: HistorialFragment){
        floatingButtonHistorial.visibility = View.GONE
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_fragment_certificaciones,fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()}
    }
}