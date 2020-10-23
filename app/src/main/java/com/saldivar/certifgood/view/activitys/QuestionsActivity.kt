package com.saldivar.certifgood.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.saldivar.certifgood.R
import com.saldivar.certifgood.view.fragments.ChronometerFragment
import com.saldivar.certifgood.view.fragments.ListCertificationsFragment

class QuestionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preguntas)
        supportActionBar?.hide()
        openFragment(ChronometerFragment.newInstance())
    }

    private fun openFragment(fragment: ChronometerFragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerCronometro,fragment)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()}
    }

}