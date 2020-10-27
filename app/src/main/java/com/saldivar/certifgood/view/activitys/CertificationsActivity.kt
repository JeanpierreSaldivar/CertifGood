package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentTransaction
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.utils.SwitchFragment
import com.saldivar.certifgood.view.fragments.ListCertificationsFragment
import com.saldivar.zkflol.utils.permissionsAndConexion.CheckInternetConnection
import kotlinx.android.synthetic.main.activity_certificaciones.*

class CertificationsActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificaciones)
        supportActionBar?.hide()
        ui()
        openFragment(ListCertificationsFragment.newInstance())

    }

    private fun ui() {
        back_flecha.setOnClickListener(this@CertificationsActivity)
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
                openFragment(ListCertificationsFragment.newInstance())
            } else {
                ShowDialog.dialogShowOptions(
                    "¿Desea cerrar la sesion?",
                    this@CertificationsActivity
                )
            }
        }else{
            ShowDialog.dialogShow("Compruebe su conexion a internet", this@CertificationsActivity)
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
        }
    }
}