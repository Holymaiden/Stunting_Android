package com.example.stunting.presenter

import android.annotation.SuppressLint
import com.example.stunting.session.SessionManager
import com.example.stunting.network.ApiService
import com.example.stunting.view.LoginView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

interface LoginPresenter {
    fun loginUser(username: String, password: String)
    fun registerUser(username: String, password: String)
}

class LoginPresenterImpl(private val view: LoginView, private val apiService: Retrofit, private var sessionManager: SessionManager) : LoginPresenter {

        @OptIn(DelicateCoroutinesApi::class)
        override fun loginUser(username: String, password: String) {
            view.showLoading()

            val service = apiService.create(ApiService::class.java)
            GlobalScope.launch {
                service.login(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.hideLoading()
                        if (it.code == 200) {
                            sessionManager.login(it.data.id,it.data.username)
                            view.onSuccessLogin(it.data)
                        } else {
                            view.onErrorLogin("Akun tidak ditemukan")
                        }
                    }, {
                        view.hideLoading()
                        it.localizedMessage?.let { it1 -> view.onErrorLogin(it1) }
                    })
            }
        }

        @SuppressLint("CheckResult")
        @OptIn(DelicateCoroutinesApi::class)
        override fun registerUser(username: String, password: String) {
            view.showLoading()

            val service = apiService.create(ApiService::class.java)
            GlobalScope.launch {
                service.register(username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ it ->
                        view.hideLoading()
                        if (it.code == 200) {
                            service.login(username, password)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({its ->
                                    view.hideLoading()
                                    if (its.code == 200) {
                                        view.onSuccessRegister("Akun berhasil dibuat")
                                    } else {
                                        view.onErrorLogin("Akun tidak ditemukan")
                                    }
                                }, {
                                    it.localizedMessage?.let { it1 -> view.onErrorLogin(it1) }
                                })
                        } else {
                            view.onErrorLogin("Akun sudah terdaftar")
                        }
                    }, {
                        view.hideLoading()
                       view.onErrorLogin("Akun sudah terdaftar")
                    })
            }
        }
}