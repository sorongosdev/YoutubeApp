package com.example.youtubeapp

import android.content.Context
import com.google.gson.Gson
import java.io.IOException

fun <T> Context.readData(fileName: String, classT: Class<T>): T? {
    return try {
        val inputStream = this.resources.assets.open(fileName)
        val byteArray = ByteArray(inputStream.available())
        inputStream.read(byteArray)
        inputStream.close() // 다 읽었으면 닫아줌

        val gson = Gson()
        gson.fromJson(inputStream.toString(), classT) // inputStream을 문자열로 바꾸고, classT로 가져옴


    } catch(e: IOException){
        null
    }
}

