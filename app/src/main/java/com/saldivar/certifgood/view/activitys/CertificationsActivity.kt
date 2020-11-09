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
    private val viewModel by lazy{ this.viewModel()}
    private val prefs by lazy { this.preferences() }
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_CertificacionesActivity)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificaciones)
        supportActionBar?.hide()
        ui()
        openFragment(ListCertificationsFragment.newInstance())
        activarFloatingButton()
    }

    private fun ui() {
        textHolaUser.text =prefs.getString(getString(R.string.name_User),getString(R.string.name_user_show))
        imagen_user.loadByUrlPicaso( prefs.getString(getString(R.string.foto_User),"")!!,R.drawable.ic_usuario_defecto)
        back_flecha.setOnClickListener(this@CertificationsActivity)
        floatingButtonHistorial.setOnClickListener(this@CertificationsActivity)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.back_flecha->{ consultarFragmentMostreado() }
            R.id.floatingButtonHistorial-> openFragmentHistorial(HistorialFragment.newInstance())
        }
    }

    override fun onBackPressed() {
        consultarFragmentMostreado()
    }

    private fun openFragment(fragment: ListCertificationsFragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_fragment_certificaciones,fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)
            commit()}
    }

    private fun activarFloatingButton() {
        val user = prefs.getString(getString(R.string.email_User), "")!!
        viewModel.sizeHistorial(user).observeForever {
            if(it!=0){ floatingButtonHistorial.visibility= View.VISIBLE }
        }
    }

    private fun consultarFragmentMostreado() {
        if(CheckInternetConnection.validateInternetConnection(this@CertificationsActivity)) {
            if (SwitchFragment.numeroFragmentMostrado == 2) {
                floatingButtonHistorial.visibility = View.VISIBLE
                openFragment(ListCertificationsFragment.newInstance())
            } else { dialogShowOption(getString(R.string.consultar_cerrar_sesion)) }

        }else{ dialogShow(getString(R.string.text_error_conexion_internet)) }
    }

    private fun dialogShowOption(message:String){
        ShowDialog.dialogShowOptions(message = message, context = this@CertificationsActivity)
    }

    private fun dialogShow(message:String){
        ShowDialog.dialogShow(message = message, context = this@CertificationsActivity)
    }

    internal fun backLoginActivity(context: CertificationsActivity) {
         context.apply {
             startActivity(Intent(context, LoginActivity::class.java))
             overridePendingTransition(R.anim.right_in, R.anim.right_out)
             finish()
         }
    }

    private fun openFragmentHistorial(fragment: HistorialFragment){
        floatingButtonHistorial.visibility = View.GONE
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_fragment_certificaciones,fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)
            commit()}
    }
}