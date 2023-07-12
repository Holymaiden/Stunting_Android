package com.example.stunting.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.stunting.R
import com.example.stunting.presenter.StuntingPresenterImpl
import com.example.stunting.session.SessionManager
import com.example.stunting.view.StuntingView

class StuntingActivity: AppCompatActivity(), StuntingView{
        private lateinit var presenter: StuntingPresenterImpl
        private lateinit var sessionManager: SessionManager

        override fun onCreate(savedInstanceState: android.os.Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_stunting)

            // membuat instance dari SessionManager
            sessionManager = SessionManager(this)
            presenter = StuntingPresenterImpl(this, this)

            val btnBack = findViewById<android.widget.ImageView>(R.id.btnBack)
            val btnPeriksa = findViewById<Button>(R.id.periksa)
            val editNama = findViewById<EditText>(R.id.editNama)
            val editUmur = findViewById<EditText>(R.id.editUmur)
            val editBerat = findViewById<EditText>(R.id.editBerat)
            val editTinggi = findViewById<EditText>(R.id.editTinggi)

            // EditUmur max number 1-60
            editUmur.addTextChangedListener(object : android.text.TextWatcher {
                @SuppressLint("SetTextI18n")
                override fun afterTextChanged(s: android.text.Editable?) {
                    val input = s.toString()
                    if (!input.isNullOrEmpty()) {
                        val number = input.toInt()
                        if (number > 60 ) {
                            editUmur.setText("60")
                        }
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Do Nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // Do Nothing
                }
            })

            btnBack.setOnClickListener {
                super.onBackPressed()
            }



            btnPeriksa.setOnClickListener {
                val editNama = editNama.text.toString()
                val editUmur = editUmur.text.toString()
                val editBerat = editBerat.text.toString()
                val editTinggi = editTinggi.text.toString()

                if (editNama.isNotEmpty() && editUmur.isNotEmpty() && editBerat.isNotEmpty() && editTinggi.isNotEmpty()){
                    presenter.createStunting(sessionManager.getId(),editNama, editUmur.toInt(), editTinggi.toFloat(), editBerat.toFloat(), "")
                }else{
                    android.widget.Toast.makeText(this, "Please fill all the fields", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onSuccess(msg: String) {
            val view = View.inflate(this, R.layout.dialog_view, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val btnOk = view.findViewById<Button>(R.id.btn_confirm)
            val text = view.findViewById<android.widget.TextView>(R.id.text_dialog)
            text.text = msg
            btnOk.setOnClickListener {
                dialog.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        override fun onError(msg: String) {
            val view = View.inflate(this, R.layout.dialog_view_err, null)
            val builder = AlertDialog.Builder(this)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val btnOk = view.findViewById<Button>(R.id.btn_confirm)
            val text = view.findViewById<android.widget.TextView>(R.id.text_dialog)
            text.text = msg
            btnOk.setOnClickListener {
                dialog.dismiss()
            }
        }

        override fun SendData(data: List<com.example.stunting.response.Stunting>) {
        }

        override fun onDelete() {
        }
}