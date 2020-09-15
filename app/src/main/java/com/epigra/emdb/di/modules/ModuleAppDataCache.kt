package com.gayebilisim.belsis.di.modules

import com.epigra.emdb.utils.managers.AppDataCacheManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ModuleAppDataCache {

    @Provides
    @Singleton
    fun provideAppDataCaches(): AppDataCacheManager {
        return AppDataCacheManager
    }
}