package com.example.youtubeapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException

fun <T> Context.readData(fileName: String, classT: Class<T>): T? {
    return try {
        val inputStream = this.resources.assets.open(fileName)
        val byteArray = ByteArray(inputStream.available())
        inputStream.read(byteArray)
        inputStream.close() // 다 읽었으면 닫아줌

        val gson = Gson()
        gson.fromJson(String(byteArray), classT) // byteArray를 문자열로 바꾸고, classT로 가져옴


    } catch(e: IOException){
        null
    } catch(e: JsonSyntaxException){
        e.printStackTrace()
        null
    }
}

