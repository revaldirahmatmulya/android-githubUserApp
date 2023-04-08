package com.revaldi.githubapp.ui.theme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.revaldi.githubapp.R
import com.revaldi.githubapp.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var splash:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        splash = findViewById(R.id.iv_splash)
        splash.alpha = 0f
        splash.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
    }
}