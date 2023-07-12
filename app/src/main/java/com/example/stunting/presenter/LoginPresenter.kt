package com.example.stunting.presenter

import android.annotation.SuppressLint
import com.example.stunting.session.SessionManager
import com.example.stunting.view.LoginView
import com.example.stunting.SqliteHelper.DBHelper

interface LoginPresenter {
    fun loginUser(username: String, password: String)
    fun registerUser(username: String, password: String)
}

class LoginPresenterImpl(private val view: LoginView, private var sessionManager: SessionManager) : LoginPresenter {

        override fun loginUser(username: String, password: String) {
            view.showLoading()

            val db = DBHelper(view as android.content.Context)
            val data = db.findDataProfile(username, password)
            if (data.id != 0) {
                sessionManager.login(data.id, data.username)
                view.onSuccessLogin(data)
            } else {
                view.onErrorLogin("Akun tidak ditemukan")
            }

        }

        @SuppressLint("CheckResult")
        override fun registerUser(username: String, password: String) {
            view.showLoading()

            val db = DBHelper(view as android.content.Context)
            val data = db.addDataProfile(username, password)
            if (data){
                view.onSuccessRegister("Akun berhasil dibuat")
            } else {
                view.onErrorLogin("Akun sudah terdaftar")
            }

        }
}