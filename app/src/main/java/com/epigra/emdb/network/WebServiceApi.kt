package com.epigra.emdb.network

import com.epigra.emdb.model.ResponseModel_Genres
import com.epigra.emdb.model.ResponseModel_Movies
import com.epigra.emdb.model.ResponseModel_Token
import com.epigra.emdb.model.base.BaseResponseModel
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException
import retrofit2.http.*
import java.io.EOFException


interface WebServiceApi {

    @POST("auth/request_token")
    fun getToken(): Single<ResponseModel_Token>

    @GET("discover/movie?api_key&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page}")
    fun getMovies(@Query("api_key") api_key: String, @Query("page") page: Int): Single<ResponseModel_Movies>

    @GET("movie/{id}/recommendations?api_key&language=en-US&page=1}")
    fun getRecommendedMovies(@Path("id") id: Int, @Query("api_key") api_key: String): Single<ResponseModel_Movies>

    @GET("genre/movie/list?api_key&language=en-US&page=1}")
    fun getGenres(@Query("api_key") api_key: String): Single<ResponseModel_Genres>

    /**
     * A generic callback-decompose class to let listeners know about the
     * response.
     */
    abstract class BaseCallBack<T : BaseResponseModel<*>> : DisposableSingleObserver<T>() {
        abstract fun onSuccessResponse(t: T?)

        abstract fun onError(statusCode: Int?, errorMessage: String?)

        override fun onError(e: Throwable) {
            if (e is HttpException) {
                onError((e as HttpException).code(), (e).message())
            } else if (e is EOFException) {
                onError(400, (e).message)
            }
        }

        override fun onSuccess(t: T) {
            onSuccessResponse(t)
        }
    }
}