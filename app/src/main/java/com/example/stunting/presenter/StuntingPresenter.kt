package com.example.stunting.presenter

import com.example.stunting.network.StuntingService
import com.example.stunting.view.StuntingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import NetworkManager
import android.content.Context
import android.util.Log
import com.example.stunting.SqliteHelper.DBHelper

interface StuntingPresenter {
    fun createStunting(user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String)
    fun getStunting(user_id: Int, limit: Int, search: String)
    fun deleteStunting(id: Int)
}

class StuntingPresenterImpl(private val view: StuntingView, private val apiService: Retrofit, private val context: Context) : StuntingPresenter {
        @OptIn(DelicateCoroutinesApi::class)
        override fun createStunting(user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String) {

            if(NetworkManager.isNetworkAvailable(context)) {
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
            }else{
                val db = DBHelper(context)
                val result = db.addData(user_id, username, umur, tinggi_badan, berat_badan, stunting)
                if (result) {
                    view.onSuccess("Stunting berhasil dibuat di local")
                } else {
                    view.onError("Stunting gagal dibuat di local")
                }
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        override fun getStunting(user_id: Int, limit: Int, search: String) {
            val db = DBHelper(context)
            if(NetworkManager.isNetworkAvailable(context)) {
                val service = apiService.create(StuntingService::class.java)
                GlobalScope.launch {
                    service.get(user_id, limit, search)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (it.code == 200) {
                                db.deleteAllData2()
                                view.SendData(it.data)
                                it.data.forEach { data ->
                                    db.addData2(data.id, data.user_id, data.username, data.umur, data.tinggi_badan, data.berat_badan, data.stunting)
                                }
//                                view.onSuccess("Stunting berhasil diambil")
                            } else {
                                view.onError("Stunting gagal diambil")
                            }
                        }, {
                            it.localizedMessage?.let { it1 -> view.onError(it1) }
                        })
                }
            }else{
                val result = db.getData2(user_id, limit, search)
                if (result.isNotEmpty()) {
                    view.SendData(result)
//                    view.onSuccess("Stunting berhasil diambil dari local")
                } else {
                    view.onError("Stunting gagal diambil dari local")
                }
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        override fun deleteStunting(id: Int) {

            if(NetworkManager.isNetworkAvailable(context)) {
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
            }else{
                val db = DBHelper(context)
                val result = db.addData3(id)
                if (result) {
                    view.onDelete()
                } else {
                    view.onDelete()
                }
            }
        }
}