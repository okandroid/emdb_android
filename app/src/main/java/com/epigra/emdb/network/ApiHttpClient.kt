package com.epigra.emdb.network


import android.os.Build
import com.epigra.emdb.BuildConfig
import com.epigra.emdb.utils.T
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * An [OkHttpClient] instance to send headers and watch out the response body.
 */
object ApiHttpClient {
    /**
     * User agent of each request which specifies the call's origin.
     */
    private val userAgent =
        "EMDB Android App : ${Build.VERSION.SDK_INT}/${BuildConfig.VERSION_NAME}"

    fun getInstance(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(T.NetworkPrefs.timeoutSec, TimeUnit.SECONDS)
        .readTimeout(T.NetworkPrefs.timeoutSec, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", T.APPLICATION_JSON)
                .addHeader(
                    "Authorization",
                    T.NetworkPrefs.authorizationKey
                )
            chain.proceed(request.build())
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .retryOnConnectionFailure(true)
        .build()
}