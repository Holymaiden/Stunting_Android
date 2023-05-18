package com.example.stunting.presenter

import com.example.stunting.network.ProfileService
import com.example.stunting.session.SessionManager
import com.example.stunting.view.ProfileView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

interface ProfilePresenter {
    fun updateProfile(username: String, password: String)
}

class ProfilePresenterImpl(private val view: ProfileView, private val apiService: Retrofit, private var sessionManager: SessionManager) : ProfilePresenter {
        @OptIn(DelicateCoroutinesApi::class)
        override fun updateProfile(username: String, password: String) {
            view.showLoading()

            val service = apiService.create(ProfileService::class.java)
            val id = sessionManager.getId()
            GlobalScope.launch {
                service.login(id, username, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view.hideLoading()
                        if (it.code == 200) {
                            view.onSuccess("Akun berhasil diubah")
                        } else {
                            view.onError("Akun tidak ditemukan")
                        }
                    }, {
                        view.hideLoading()
                        it.localizedMessage?.let { it1 -> view.onError(it1) }
                    })
            }
        }
}