package com.example.stunting.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Int>
)

