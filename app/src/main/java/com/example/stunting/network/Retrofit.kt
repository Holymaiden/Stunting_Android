package com.example.stunting.network

class Retrofit {
    companion object {
        const val BASE_URL = "https://stunting.fihaa-app.com/";

        fun getInstance(): retrofit2.Retrofit {
            return retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}