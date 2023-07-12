package com.example.stunting.activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import com.example.stunting.R
import com.example.stunting.SqliteHelper.DBHelper

class IntroActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val btnMulai = findViewById<android.widget.ImageView>(R.id.btnMulai)

        dbHelper = DBHelper(this)
        val db: SQLiteDatabase = dbHelper.writableDatabase

        btnMulai.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}