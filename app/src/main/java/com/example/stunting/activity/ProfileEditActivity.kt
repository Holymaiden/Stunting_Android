package com.example.stunting.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.stunting.R
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.stunting.network.Retrofit
import com.example.stunting.presenter.ProfilePresenterImpl
import com.example.stunting.session.SessionManager
import com.example.stunting.view.ProfileView

class ProfileEditActivity: AppCompatActivity(), ProfileView {
    private lateinit var presenter: ProfilePresenterImpl
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)

        // membuat instance dari SessionManager
        sessionManager = SessionManager(this)
        presenter = ProfilePresenterImpl(this, Retrofit.getInstance(), sessionManager)

        val btnBack = findViewById<android.widget.ImageView>(R.id.btnBack)
        val btnSave = findViewById<android.widget.Button>(R.id.btnSave)
        val editUsername = findViewById<android.widget.EditText>(R.id.editUsername)
        val editPassword = findViewById<android.widget.EditText>(R.id.editPassword)
        val editPassword2 = findViewById<android.widget.EditText>(R.id.editPassword2)

        editUsername.setText(sessionManager.getUsername())

        btnBack.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnSave.setOnClickListener {
            val editUsername = editUsername.text.toString()
            val editPassword = editPassword.text.toString()
            val editPassword2 = editPassword2.text.toString()

            if (editUsername.isNotEmpty() && editPassword.isNotEmpty() && editPassword2.isNotEmpty()){
                if(editPassword != editPassword2){
                    android.widget.Toast.makeText(this, "Password doesn't match", android.widget.Toast.LENGTH_SHORT).show()
                }else{
                    presenter.updateProfile(editUsername, editPassword)
                }
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
        text.text = "Profile Berhasil Diubah"
        btnOk.setOnClickListener {
            dialog.dismiss()
            val intent = android.content.Intent(this, ProfileActivity::class.java)
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
        text.text = "Username telah digunakan"
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}