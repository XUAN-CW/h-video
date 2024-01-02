package com.xuanchengwei.baizizhan.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xuanchengwei.hvideoapp.adapter.LocalDateTimeAdapter
import java.lang.reflect.Type
import java.time.LocalDateTime

object JsonUtils {
    val gson: Gson
        get() = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create()


    fun <E> deepCopy(src: E, typeOfSrc: Type): E {
        val json = gson.toJson(src, typeOfSrc)
        return gson.fromJson(json, typeOfSrc)
    }
}