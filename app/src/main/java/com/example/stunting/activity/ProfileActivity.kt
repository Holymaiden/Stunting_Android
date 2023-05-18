package com.example.stunting.activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast
import com.example.stunting.R
import com.example.stunting.session.SessionManager

class ProfileActivity: AppCompatActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // membuat instance dari SessionManager
        sessionManager = SessionManager(this)

        val btnHome = findViewById<android.widget.ImageView>(R.id.imageView9)
        val btnStunting = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnStunting)
        val btnAccount = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.btnAccount)
        val btnLogout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.btnLogout)

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnStunting.setOnClickListener {
            val intent = Intent(this, StuntingActivity::class.java)
            startActivity(intent)
        }

        btnAccount.setOnClickListener {
            val intent = Intent(this, ProfileEditActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            sessionManager.logout()
            Toast.makeText(this, "Anda Telah Logout", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignInActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}