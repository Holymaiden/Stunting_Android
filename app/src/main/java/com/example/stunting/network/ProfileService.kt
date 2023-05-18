package com.example.stunting.network

import retrofit2.http.*
import com.example.stunting.response.ProfileResponse
import io.reactivex.Single

interface ProfileService {

    @FormUrlEncoded
    @PUT("users/{id}")
    fun login(
        @Path("id") id: Int,
        @Field("username") username: String,
        @Field("password") password: String,
    ): Single<ProfileResponse>
}