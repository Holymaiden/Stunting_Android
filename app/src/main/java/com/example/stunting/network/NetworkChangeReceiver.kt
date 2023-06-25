package com.example.stunting.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.stunting.SqliteHelper.DBHelper
import com.example.stunting.response.Stunting
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkChangeReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

            if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) {
                // Koneksi internet tersedia, lakukan pengiriman data ke API
                sendDataToApi(context)
                deleteDataFromDb(context)
            }
    }

    companion object {
        fun sendDataToApi(context: Context) {
            // Lakukan pengiriman data ke API
            val db = DBHelper(context)
            val data: List<Stunting> = db.getData()
            Log.d("data", "Send data to API")
            Log.d("Stunting", data.toString())
            val apiService = Retrofit.getInstance()
            val service = apiService.create(StuntingService::class.java)

            for (i in data.indices) {
                Log.d("Stunting Detail", data[i].toString())
                service.create(
                    data[i].user_id,
                    data[i].username,
                    data[i].umur,
                    data[i].tinggi_badan,
                    data[i].berat_badan,
                    data[i].stunting
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}) {
                        Log.d("Stunting Error", it.localizedMessage)
                    }
            }

            db.deleteAllData()
        }

        fun deleteDataFromDb(context: Context) {
            // Hapus data dari database
            val db = DBHelper(context)
            val data: List<Int> = db.getData3()
            val apiService = Retrofit.getInstance()
            val service = apiService.create(StuntingService::class.java)

            for (i in data.indices) {
                service.delete(data[i])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}) {
                        Log.d("Stunting Error", it.localizedMessage)
                    }
            }

            db.deleteAllData3()
        }
    }
}