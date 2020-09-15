package com.smartral.esimibul.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.epigra.emdb.utils.conversation.JsonConverterFactory
import com.epigra.emdb.utils.managers.AppPreferencesManager
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern



object Utils {

    private val esimiBulDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("TR"))
    /**
     * As user jumps from screen to other screen, this checks whether the animation will be done
     * for lollipop or pre-lollipop.
     *
     * @return
     */
    fun isCompatibleForTransitionAnim(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    /**
     * Checks if mail is valid or not.
     *
     * @param mail
     * @return
     */
    fun isEmailValid(mail: String) =
        mail.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()

    /**
     * Checks if the device is connected.
     */
    @SuppressLint("MissingPermission")
    fun isConnected(context: Context?) =
        (context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected == true

    /**
     * Toggling visibility of soft keyboard.
     *
     * @param activity is the current activity that holds keyboard.
     * @param show     parameter to show/hide soft keyboard.
     */
    fun toggleKeyboard(activity: Activity, show: Boolean) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = activity.currentFocus
        currentFocus?.let {
            when {
                show -> imm.showSoftInput(currentFocus, InputMethodManager.SHOW_IMPLICIT)
                else -> imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
        }
    }

    /**
     * Shows a snackbar with the given text.
     *
     * @param view    is the layout to stick on.
     * @param message is the text to show inside snackbar.
     */
    fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    /**
     * Converts anything into a JSON string.
     *
     * @param data
     * @return
     */
    fun serializeJSON(data: Any): String {
        val converterFactory = JsonConverterFactory.getInstance()
        return converterFactory.toJson(data)
    }

    /**
     * Converts a JSON string into an object of specified class.
     *
     * @param data
     * @return
     */
    inline fun <reified T> deserializeJSON(data: String?): T? {
        if (TextUtils.isEmpty(data)) return null
        val converterFactory = JsonConverterFactory.getInstance()
        return converterFactory.fromJson(data!!, T::class.java)
    }

    /**
     * Returns the real path of a file for the given URI.
     */
    fun getRealPathFromURI(context: Context?, contentUri: Uri?): String? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        contentUri?.let {
            val cursor = context?.contentResolver?.query(it, filePathColumn, null, null, null)
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val filePath = cursor?.getString(columnIndex!!)
            cursor?.close()
            return filePath
        }
        return null
    }

    fun isCollectionNotEmpty(collection: Collection<*>?): Boolean {
        return collection != null && !collection.isEmpty()
    }


    @JvmStatic
    fun convertTimeToDate(time: Long): String? {
        var dateFormat = "dd/MM/yyyy hh:mm:ss"
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return formatter.format(calendar.time)
    }

    /**
     * Parses a Microsoft .NET style JSON timestamp and returns a Java Date irrespective of time
     * zones (but can parse them).
     *
     * [Microsoft .NET JSON Format Reference](http://weblogs.asp.net/bleroy/archive/2008/01/18/dates-and-json.aspx)
     * [GSON Code Reference](http://benjii.me/2010/04/deserializing-json-in-android-using-gson/)
     *
     * @param msJsonDateTime The String representation of a Microsoft style timestamp
     * @return Java Date that represents the timestamp
     */
    fun parseMsTimestampToDate(msJsonDateTime: String?): Date? {
        if (msJsonDateTime == null) return null
        val pattern = Pattern.compile("/(Date\\((-.?)([+\\-].*)?\\))/")
        val matcher = pattern.matcher(msJsonDateTime)
        val ts = matcher.replaceAll("$2")
        return Date(ts.toLong())
    }

    fun getScreenHeight(activity: Activity): Int {
        val display = activity.getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        val width = size.x
        return size.y
    }

    /**
     * Removes a value from preferences that specified with a key.
     *
     * @param key
     */
    @Synchronized
    fun removePreference(key: String) {
        AppPreferencesManager.removeValue(key)
    }

}