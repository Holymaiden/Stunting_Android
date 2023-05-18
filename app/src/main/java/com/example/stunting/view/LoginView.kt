package com.example.stunting.view

import com.example.stunting.response.User

interface LoginView {
    fun onSuccessLogin(msg: User)
    fun onSuccessRegister(msg: String)
    fun onErrorLogin(msg: String)
    fun showLoading()
    fun hideLoading()
}