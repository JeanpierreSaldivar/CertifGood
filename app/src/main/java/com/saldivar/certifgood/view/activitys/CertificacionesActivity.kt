package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saldivar.certifgood.R
import com.saldivar.certifgood.utils.ShowDialog
import com.saldivar.certifgood.view.fragments.ListCertificacionesFragment
import com.saldivar.certifgood.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_certificaciones.*
import kotlinx.android.synthetic.main.activity_main.*

class CertificacionesActivity : AppCompatActivity(), View.OnClickListener{
    private val viewModel by lazy{ ViewModelProvider(this).get(MainViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certificaciones)
        supportActionBar?.hide()
        ui()
        openFragment(ListCertificacionesFragment.newInstance())

    }

    private fun ui() {
        back_flecha.setOnClickListener(this@CertificacionesActivity)
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply{
            replace(R.id.container_fragment_certificaciones,fragment)
            addToBackStack(null)
            commit()
        }
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.back_flecha->{ShowDialog.dialogShowOptions("¿Desea cerrar la sesion?",this@CertificacionesActivity)}
        }
    }
}