package com.example.stunting.presenter

import com.example.stunting.view.StuntingView
import android.content.Context
import com.example.stunting.SqliteHelper.DBHelper
import com.example.stunting.helper.naivebayes

interface StuntingPresenter {
    fun createStunting(user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String)
    fun getStunting(user_id: Int, limit: Int, search: String)
    fun deleteStunting(id: Int)
}

class StuntingPresenterImpl(private val view: StuntingView, private val context: Context) : StuntingPresenter {
        override fun createStunting(user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String) {
                val db = DBHelper(context)

                val prosesStunting = naivebayes(umur, tinggi_badan, berat_badan, context)
                prosesStunting.naivebayes()
                val hasil = prosesStunting.proses()

                val result = db.addData(user_id, username, umur, tinggi_badan, berat_badan, hasil)
                if (result) {
                    view.onSuccess("Stunting berhasil dibuat")
                } else {
                    view.onError("Stunting gagal dibuat")
                }
        }

        override fun getStunting(user_id: Int, limit: Int, search: String) {
            val db = DBHelper(context)
                val result = db.getData(user_id, limit, search)
                if (result.isNotEmpty()) {
                    view.SendData(result)
                } else {
                    view.SendData(result)
                    view.onError("Data tidak ditemukan")
                }
        }

        override fun deleteStunting(id: Int) {
                val db = DBHelper(context)
                val result = db.deleteData(id)
                if (result) {
                    view.onDelete()
                } else {
                    view.onDelete()
                }
            }
}