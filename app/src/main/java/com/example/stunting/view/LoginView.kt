package com.example.stunting.view

interface LoginView {
    fun onSuccessLogin(msg: Any)
    fun onSuccessRegister(msg: String)
    fun onErrorLogin(msg: String)
    fun showLoading()
    fun hideLoading()
}