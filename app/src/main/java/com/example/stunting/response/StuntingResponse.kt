package com.example.stunting.response

import com.google.gson.annotations.SerializedName

data class GetStuntingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Stunting>
)

data class CreateStuntingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Int>
)

data class DeleteStuntingResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Int
)

data class Stunting(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("umur")
    val umur: Int,
    @SerializedName("tinggi_badan")
    val tinggi_badan: Float,
    @SerializedName("berat_badan")
    val berat_badan: Float,
    @SerializedName("stunting")
    val stunting: String,
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("updated_at")
    val updated_at: String,
)
