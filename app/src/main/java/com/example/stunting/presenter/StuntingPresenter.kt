package com.example.stunting.presenter

import com.example.stunting.network.StuntingService
import com.example.stunting.view.StuntingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

interface StuntingPresenter {
    fun createStunting(user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String)
    fun getStunting(user_id: Int, limit: Int, search: String)
    fun deleteStunting(id: Int)
}

class StuntingPresenterImpl(private val view: StuntingView, private val apiService: Retrofit) : StuntingPresenter {
        @OptIn(DelicateCoroutinesApi::class)
        override fun createStunting(user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String) {

            val service = apiService.create(StuntingService::class.java)
            GlobalScope.launch {
                service.create(user_id, username, umur, tinggi_badan, berat_badan, stunting)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.code == 200) {
                            view.onSuccess("Stunting berhasil dibuat")
                        } else {
                            view.onError("Stunting gagal dibuat")
                        }
                    }, {
                        it.localizedMessage?.let { it1 -> view.onError(it1) }
                    })
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        override fun getStunting(user_id: Int, limit: Int, search: String) {

            val service = apiService.create(StuntingService::class.java)
            GlobalScope.launch {
                service.get(user_id, limit, search)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.code == 200) {
                            view.SendData(it.data)
                            view.onSuccess("Stunting berhasil diambil")
                        } else {
                            view.onError("Stunting gagal diambil")
                        }
                    }, {
                        it.localizedMessage?.let { it1 -> view.onError(it1) }
                    })
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        override fun deleteStunting(id: Int) {
            val service = apiService.create(StuntingService::class.java)
            GlobalScope.launch {
                service.delete(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.code == 200) {
                            view.onDelete()
                        } else {
                            view.onDelete()
                        }
                    }, {
                        it.localizedMessage?.let { it1 -> view.onError(it1) }
                    })
            }
        }
}