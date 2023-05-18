package com.example.stunting.network

import retrofit2.http.*
import com.example.stunting.model.UserModel
import com.example.stunting.response.UserResponse
import com.example.stunting.response.RegisterResponse
import io.reactivex.Single

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Single<UserResponse>

    @FormUrlEncoded
    @POST("users")
    fun register(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Single<RegisterResponse>

}