package com.epigra.emdb.utils

import android.Manifest
import android.os.Environment
import com.epigra.emdb.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

object T {

    val ACTIVITY_BUNDLE_EXTRA = "${BuildConfig.APPLICATION_ID}_BUNDLE_EXTRA"
    val APPLICATION_JSON = "application/json"
    val PREREFENCES = "${BuildConfig.APPLICATION_ID}_preferences"
    val JSON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    object UserPrefs {
        const val LOGGED_IN_USER_AUTH_TOKEN = "LOGGED_IN_USER_AUTH_TOKEN"
        const val LOGGED_IN_USER_DATA = "LOGGED_IN_USER_DATA"
        const val LOGGED_IN_USER_LOCATIONS = "LOGGED_IN_USER_LOCATIONS"
        const val LOGGED_IN_USER_NOTIF_TOKEN = "LOGGED_IN_USER_NOTIF_TOKEN"
        const val LOGGED_IN_USER_SETTINGS = "LOGGED_IN_USER_SETTINGS"
        const val LOGGED_IN_USER_PHOTOS = "LOGGED_IN_USER_PHOTOS"
        const val PROMO_CONTENT = "PROMO_CONTENT"
        const val MOVIE_GENRES = "MOVIE_GENRES"
    }

    object ApiParams {
        val API_KEY = "285bf70fa86a206f2f4d649dd3cddf38"
        val BASE_URL = "https://api.themoviedb.org/3/"
        val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    object RequestCodes {
        val REQUEST_MOVIE_DETAIL_ACTION = 1004
    }

    object BundleExtra {
        val MOVIE_DATA = "MOVIE_DATA"
    }

    object NetworkPrefs {
        const val timeoutSec = 30L // in seconds.
        const val authorizationKey =
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyODViZjcwZmE4NmEyMDZmMmY0ZDY0OWRkM2NkZGYzOCIsInN1YiI6IjVmNWNhNTIwNGYzM2FkMDAzOGY1ZDRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.2EeLLjaybL17ArVxZp-DsRTcYEwhvhV54wUq8KO6uMU"
    }

    /**
     * Manifest permission definitions if needed
     */
    object Permissions {
        // Permission types.
        val ACCESS_FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
        val ACCESS_COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION
        val CAMERA_PERMISSION = Manifest.permission.CAMERA
        val GET_ACCOUNTS_PERMISSION = Manifest.permission.GET_ACCOUNTS
        val WRITE_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
        val READ_CONTACTS_PERMISSION = Manifest.permission.READ_CONTACTS
        val WRITE_CONTACTS_PERMISSION = Manifest.permission.WRITE_CONTACTS
        val CALL_PHONE_PERMISSION = Manifest.permission.CALL_PHONE
    }

    /**
     * Status codes for handling server error if needed
     */
    object StatusCodes {
        const val UNAUTHORIZED = 401
        const val PAYMENT_REQUIRED = 402
        const val FORBIDDEN = 403
        const val NOT_ACCEPTABLE = 406
        const val PRECONDITION_FAILED = 412
        const val SERVER_ERROR = 500
    }

}