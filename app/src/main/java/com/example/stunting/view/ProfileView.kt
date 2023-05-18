package com.example.stunting.view

interface ProfileView {
    fun onSuccess(msg: String)
    fun onError(msg: String)
    fun showLoading()
    fun hideLoading()
}