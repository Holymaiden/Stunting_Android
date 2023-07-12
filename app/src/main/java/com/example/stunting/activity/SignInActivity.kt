package com.example.stunting.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.stunting.R
import com.example.stunting.presenter.LoginPresenterImpl
import com.example.stunting.view.LoginView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.stunting.session.SessionManager

class SignInActivity : AppCompatActivity(), LoginView {
    private lateinit var presenter: LoginPresenterImpl
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // membuat instance dari SessionManager
        sessionManager = SessionManager(this)
        presenter = LoginPresenterImpl(this, sessionManager)

        // cek apakah user sudah login
        if (sessionManager.isLoggedIn()){
            val intent = android.content.Intent(this, MainActivity::class.java)
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK or android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        val btnSignIn = findViewById<Button>(R.id.btnLogin)
        val editUsername = findViewById<EditText>(R.id.editUsername)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val textView7 = findViewById<android.widget.TextView>(R.id.textView7)

        btnSignIn.setOnClickListener {
            val editUsername = editUsername.text.toString()
            val editPassword = editPassword.text.toString()

            if (editUsername.isNotEmpty() && editPassword.isNotEmpty()){
                presenter.loginUser(editUsername, editPassword)
            }else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        textView7.setOnClickListener {
            val intent = android.content.Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showLoading() {
        val progressBar = findViewById<android.widget.ProgressBar>(R.id.progressBar)
        progressBar.visibility = android.view.View.VISIBLE
    }

    override fun hideLoading() {
        val progressBar = findViewById<android.widget.ProgressBar>(R.id.progressBar)
        progressBar.visibility = android.view.View.GONE
    }

    override fun onSuccessLogin(msg: Any) {
        val view = View.inflate(this, R.layout.dialog_view, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val btnOk = view.findViewById<Button>(R.id.btn_confirm)
        val text = view.findViewById<android.widget.TextView>(R.id.text_dialog)
        text.text = "Selamat datang Kembali ${sessionManager.getUsername()}"
        btnOk.setOnClickListener {
            dialog.dismiss()
            val intent = android.content.Intent(this, MainActivity::class.java)
            intent.setFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK or android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onErrorLogin(msg: String) {
        val view = View.inflate(this, R.layout.dialog_view_err, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val btnOk = view.findViewById<Button>(R.id.btn_confirm)
        val text = view.findViewById<android.widget.TextView>(R.id.text_dialog)
        text.text = "Username atau Password salah"
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onSuccessRegister(msg: String) {
        // Do nothing
    }
}