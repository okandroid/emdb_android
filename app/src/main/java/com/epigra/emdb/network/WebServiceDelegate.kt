package com.epigra.emdb.network

import com.epigra.emdb.di.components.DaggerComponentApi
import com.epigra.emdb.model.ResponseModel_Genres
import com.epigra.emdb.model.ResponseModel_Movies
import com.epigra.emdb.model.ResponseModel_Token
import io.reactivex.Single
import javax.inject.Inject

class WebServiceDelegate {

    @Inject
    lateinit var api: WebServiceApi

    init {
        DaggerComponentApi.create().injectApi(this)
    }

    fun getToken(): Single<ResponseModel_Token> {
        return api.getToken()
    }

    fun getMovies(key: String, page: Int): Single<ResponseModel_Movies> {
        return api.getMovies(key, page)
    }

    fun getRecommendedMovies(movieId: Int, key: String): Single<ResponseModel_Movies> {
        return api.getRecommendedMovies(movieId, key)
    }

    fun getGenres(key: String): Single<ResponseModel_Genres> {
        return api.getGenres(key)
    }
}