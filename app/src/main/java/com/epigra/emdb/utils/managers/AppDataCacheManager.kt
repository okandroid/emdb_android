package com.epigra.emdb.utils.managers

import com.epigra.emdb.core.AppEmdb
import com.epigra.emdb.model.ResponseModel_Genres
import com.epigra.emdb.utils.T
import com.smartral.esimibul.utils.Utils

import java.io.Serializable

object AppDataCacheManager {

    /**
     * Deletes caches and removes database.
     */
    fun clearCaches() {
        // Clear caches.
        Utils.apply {
            removePreference(T.UserPrefs.LOGGED_IN_USER_AUTH_TOKEN)
            removePreference(T.UserPrefs.MOVIE_GENRES)
        }
    }

    fun saveGenres(data: ResponseModel_Genres) {
        AppPreferencesManager.saveStringValue(
            T.UserPrefs.MOVIE_GENRES,
            Utils.serializeJSON(data)
        )
    }

    fun getGenres(): ResponseModel_Genres? {
        val data =
            AppPreferencesManager.getStringValue(T.UserPrefs.MOVIE_GENRES)
        return Utils.deserializeJSON(data)
    }
}