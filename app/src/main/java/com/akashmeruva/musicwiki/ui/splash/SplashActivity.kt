package com.akashmeruva.musicwiki.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.akashmeruva.musicwiki.R
import com.akashmeruva.musicwiki.ui.Genre.Genre_Activity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                val intent = Intent(applicationContext , Genre_Activity::class.java)
                startActivity(intent)
                this.finish()
            }, 1500)

    }
}