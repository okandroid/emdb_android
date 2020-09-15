package com.epigra.emdb.utils.managers

import android.content.Context
import android.content.SharedPreferences
import com.epigra.emdb.core.AppEmdb
import com.epigra.emdb.utils.T

object AppPreferencesManager {

    private val mPref: SharedPreferences =
        AppEmdb.instance.getSharedPreferences(T.PREREFENCES, Context.MODE_PRIVATE)

    /**
     * A method that returns a string value for the given key. By default
     * it returns empty string.
     *
     * @param key is a string to get value for.
     * @return a string as a value for the given key.
     */
    fun getStringValue(key: String): String? {
        return mPref.getString(key, "")
    }

    /**
     * A method to save a string value within shared preferences for a key.
     *
     * @param key   is the unique string that saves the value for.
     * @param value is the string to be saved.
     */
    fun saveStringValue(key: String, value: String) {
        mPref.edit()
            .putString(key, value)
            .apply()
    }

    /**
     * A method that returns a boolean value for the given key. By default
     * it returns false.
     *
     * @param key is a string to get value for.
     * @return a boolean as a value for the given key.
     */
    fun getBooleanValue(key: String): Boolean {
        return mPref.getBoolean(key, false)
    }

    /**
     * A method to save a boolean value within shared preferences for a key.
     *
     * @param key   is the unique string that saves the value for.
     * @param value is the boolean to be saved.
     */
    fun saveBooleanValue(key: String, value: Boolean) {
        mPref.edit()
            .putBoolean(key, value)
            .apply()
    }

    /**
     * A method that returns an integer value for the given key. By default
     * it returns 0.
     *
     * @param key is a string to get value for.
     * @return an integer as a value for the given key.
     */
    fun getIntegerValue(key: String): Int = mPref.getInt(key, 0)

    /**
     * A method to save an integer value within shared preferences for a key.
     *
     * @param key   is the unique string that saves the value for.
     * @param value is the integer to be saved.
     */
    fun saveIntegerValue(key: String, value: Int) {
        mPref.edit()
            .putInt(key, value)
            .apply()
    }

    /**
     * Removes a value from preferences that specified with a key.
     *
     * @param key
     */
    fun removeValue(key: String) {
        mPref.edit().remove(key).apply()
    }
}
