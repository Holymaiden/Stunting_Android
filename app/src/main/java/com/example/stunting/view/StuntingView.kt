package com.example.stunting.view

import com.example.stunting.response.Stunting

interface StuntingView {
    fun onSuccess(msg: String)
    fun onError(msg: String)
    fun SendData(data: List<Stunting>)
    fun onDelete()
}