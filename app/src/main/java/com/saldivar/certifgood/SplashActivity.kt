package com.saldivar.certifgood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        nextActivity()
    }

    private fun nextActivity(){
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        finish()
    }
}