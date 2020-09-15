package com.epigra.emdb.core

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.epigra.emdb.utils.managers.AppDataCacheManager
import com.epigra.emdb.utils.managers.AppPreferencesManager

class AppEmdb : Application() {
    companion object {
        lateinit var instance: AppEmdb
    }

    init {
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    fun getAppDataCache() = AppDataCacheManager

    fun getSharedPreferencesManager() = AppPreferencesManager
}