package com.example.stunting.activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.stunting.R

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val btnMulai = findViewById<android.widget.ImageView>(R.id.btnMulai)

        btnMulai.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}