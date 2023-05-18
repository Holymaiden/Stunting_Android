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
import com.example.stunting.network.Retrofit
import com.example.stunting.session.SessionManager

class SignUpActivity : AppCompatActivity(), LoginView {

    private lateinit var presenter: LoginPresenterImpl
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // membuat instance dari SessionManager
        sessionManager = SessionManager(this)
        presenter = LoginPresenterImpl(this, Retrofit.getInstance(), sessionManager)

        val btnSignIn = findViewById<Button>(R.id.btnLogin)
        val editUsername = findViewById<EditText>(R.id.editUsername)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editConfirmPassword = findViewById<EditText>(R.id.editPassword2)
        val textView7 = findViewById<android.widget.TextView>(R.id.textView7)

        btnSignIn.setOnClickListener {
            val editUsername = editUsername.text.toString()
            val editPassword = editPassword.text.toString()
            val editConfirmPassword = editConfirmPassword.text.toString()

            if (editUsername.isNotEmpty() && editPassword.isNotEmpty() && editConfirmPassword.isNotEmpty()){
                if(editPassword != editConfirmPassword){
                    Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show()
                }else{
                    presenter.registerUser(editUsername, editPassword)
                }
            }else{
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        textView7.setOnClickListener {
            val intent = android.content.Intent(this, SignInActivity::class.java)
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

    override fun onSuccessRegister(msg: String) {
        val view = View.inflate(this, R.layout.dialog_view, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val btnOk = view.findViewById<Button>(R.id.btn_confirm)
        val text = view.findViewById<android.widget.TextView>(R.id.text_dialog)
        text.text = "Registrasi Berhasil"
        btnOk.setOnClickListener {
            dialog.dismiss()
            val intent = android.content.Intent(this, SignInActivity::class.java)
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
        text.text = "Username Telah Digunakan"
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun onSuccessLogin(msg: com.example.stunting.response.User) {
        // TODO("Not yet implemented")
    }
}