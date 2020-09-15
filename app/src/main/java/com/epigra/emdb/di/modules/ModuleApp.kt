package com.gayebilisim.belsis.di.modules

import com.epigra.emdb.core.AppEmdb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuleApp(val app: AppEmdb) {

    @Provides
    @Singleton
    fun providesApp(): AppEmdb = app
}