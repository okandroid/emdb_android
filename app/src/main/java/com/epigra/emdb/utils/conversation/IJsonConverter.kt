package com.epigra.emdb.utils.conversation

interface IJsonConverter {

  fun toJson(any: Any): String

  fun <T> fromJson(json: String, clazz: Class<T>): T

}