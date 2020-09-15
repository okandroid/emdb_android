package com.epigra.emdb.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.epigra.emdb.R
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.setLocalImage(file: File, applyCircle: Boolean = false) {
    val glide = Glide.with(this).load(file)
    if (applyCircle) {
        glide.apply(RequestOptions.circleCropTransform()).into(this)
    } else {
        glide.into(this)
    }
}


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable? = null) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.placeholder_image)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .apply(RequestOptions().optionalCircleCrop())
        .override(200, 200)
        .apply(RequestOptions().optionalCircleCrop())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun ImageView.loadImageDiscover(uri: String?, progressDrawable: CircularProgressDrawable? = null) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.placeholder_image)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .override(600, 800)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun ImageView.loadImageProfileBackground(
    uri: String?,
    progressDrawable: CircularProgressDrawable? = null
) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.placeholder_image)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .override(600, 800)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun ImageView.loadImageWithTransformation(
    uri: String?,
    progressDrawable: CircularProgressDrawable? = null
) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.placeholder_image)

    val multi = MultiTransformation<Bitmap>(
        BlurTransformation(15),
        CropTransformation(200, 200, CropTransformation.CropType.CENTER)
    )

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .apply(
            RequestOptions.bitmapTransform(multi)
        )
        .override(200, 200)
        //.apply(RequestOptions().optionalCircleCrop())
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun AppCompatTextView.convertTimeToDate(time: Long) {
    val dateFormat = "dd/MM/yyyy hh:mm"
    val formatter = SimpleDateFormat(dateFormat)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time
    this.text = formatter.format(calendar.time)
}

@BindingAdapter("android:imageUrlWithTransformation")
fun loadImageWithTransformation(view: AppCompatImageView, url: String) {
    view.loadImageWithTransformation(url, getProgressDrawable(view.context))
}

@BindingAdapter("android:convertTime")
fun convertTime(view: AppCompatTextView, time: Long) {
    view.convertTimeToDate(time)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: AppCompatImageView, url: String?) {
    if (url == null)
        view.loadImage(
            "https://www.esimibul.com/static/default-profile-photo.png",
            getProgressDrawable(view.context)
        )
    view.loadImage(url, getProgressDrawable(view.context))
}

@BindingAdapter("android:imageUrlDiscover")
fun loadImageDiscover(view: AppCompatImageView, url: String?) {
    if (url == null)
        view.loadImageDiscover(
            "https://www.esimibul.com/static/default-profile-photo.png"
        )
    view.loadImageDiscover(url)
}

@BindingAdapter("android:imageUrlProfileBackground")
fun loadImageProfileBackground(view: AppCompatImageView, url: String) {
    view.loadImageProfileBackground(url, getProgressDrawable(view.context))
}