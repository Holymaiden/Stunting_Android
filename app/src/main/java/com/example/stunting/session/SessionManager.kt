package com.example.stunting.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager (context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()
    private var mContext: Context = context

    // Simpan login status ke dalam shared preference
    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(Constants.IS_LOGIN.toString(), isLoggedIn)
        editor.apply()
    }

    // Simpan nama pengguna ke dalam shared preference
    fun setUsername(username: String) {
        editor.putString(Constants.KEY_USERNAME, username)
        editor.apply()
    }

    // Ambil nama pengguna dari shared preference
    fun getUsername(): String? {
        return sharedPreferences.getString(Constants.KEY_USERNAME, null)
    }

    // Simpan id pengguna ke dalam shared preference
    fun setId(id: Int) {
        editor.putInt(Constants.KEY_ID.toString(), id)
        editor.apply()
    }

    // Ambil id pengguna dari shared preference
    fun getId(): Int {
        return sharedPreferences.getInt(Constants.KEY_ID.toString(), 0)
    }

    // Cek login status
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(Constants.IS_LOGIN.toString(), false)
    }

    // Hapus data pengguna dari shared preference
    fun logout() {
        editor.clear()
        editor.apply()
    }

    fun login(id: Int, username: String) {
        setLogin(true)
        setId(id)
        setUsername(username)
    }
}