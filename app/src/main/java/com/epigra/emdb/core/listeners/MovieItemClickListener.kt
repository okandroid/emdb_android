package com.epigra.emdb.core.listeners

import android.view.View

interface MovieItemClickListener<T> {
    fun onMovieItemClicked(v: View, data: T)
}