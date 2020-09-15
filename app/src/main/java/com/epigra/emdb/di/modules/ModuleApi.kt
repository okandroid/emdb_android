package com.epigra.emdb.di.modules

import com.epigra.emdb.network.ApiHttpClient
import com.epigra.emdb.network.WebServiceApi
import com.epigra.emdb.network.WebServiceDelegate
import com.epigra.emdb.utils.T
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ModuleApi {

    @Provides
    fun provideApi(): WebServiceApi {
        return Retrofit.Builder()
            .baseUrl(T.ApiParams.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(ApiHttpClient.getInstance())
            .build()
            .create(WebServiceApi::class.java)
    }

    @Provides
    fun provideAnimalApiService() : WebServiceDelegate {
        return WebServiceDelegate()
    }
}