package com.example.stunting.presenter

import android.content.Context
import com.example.stunting.SqliteHelper.DBHelper
import com.example.stunting.session.SessionManager
import com.example.stunting.view.ProfileView

interface ProfilePresenter {
    fun updateProfile(username: String, password: String)
}

class ProfilePresenterImpl(private val view: ProfileView, private var sessionManager: SessionManager) : ProfilePresenter {
        override fun updateProfile(username: String, password: String) {
            view.showLoading()

            val db = DBHelper(view as Context)
            val data: DBHelper.UserObj = db.updateDataProfile(username, password)
            if (data.id != 0) {
                view.onSuccess("Akun berhasil diubah")
            } else {
                view.onError("Akun tidak ditemukan")
            }
        }
}