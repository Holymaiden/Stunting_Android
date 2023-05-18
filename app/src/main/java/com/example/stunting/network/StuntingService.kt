package com.example.stunting.network

import retrofit2.http.*
import com.example.stunting.response.CreateStuntingResponse
import com.example.stunting.response.DeleteStuntingResponse
import com.example.stunting.response.GetStuntingResponse
import io.reactivex.Single

interface StuntingService {

    @FormUrlEncoded
    @POST("stunting")
    fun create(
        @Field("user_id") user_id: Int,
        @Field("username") username: String,
        @Field("umur") umur: Int,
        @Field("tinggi_badan") tinggi_badan: Float,
        @Field("berat_badan") berat_badan: Float,
        @Field("stunting") stunting: String,
    ): Single<CreateStuntingResponse>

    @GET("stunting")
    fun get(
        @Query("user_id") user_id: Int,
        @Query("limit") limit: Int,
        @Query("search") search: String,
    ): Single<GetStuntingResponse>

    @DELETE("stunting/{id}")
    fun delete(
        @Path("id") id: Int
    ): Single<DeleteStuntingResponse>
}