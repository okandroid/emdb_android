package com.epigra.emdb.utils.conversation

import com.epigra.emdb.utils.T
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object JsonConverterFactory : IJsonConverter {

    val gson: Gson = GsonBuilder()
        .setDateFormat(T.JSON_DATE_FORMAT)
        .create()

    override fun toJson(any: Any): String {
        return gson.toJson(any)
    }

    override fun <T> fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    fun getInstance(): JsonConverterFactory {
        return this
    }
}