package com.epigra.emdb.di.components
import com.epigra.emdb.di.modules.ModuleApi
import com.epigra.emdb.network.WebServiceDelegate
import dagger.Component

@Component(modules = [ModuleApi::class])
interface ComponentApi {
    fun injectApi(service: WebServiceDelegate)
}