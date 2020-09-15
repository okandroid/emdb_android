package com.smartral.dating.sevenful.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.epigra.emdb.R
import com.epigra.emdb.core.listeners.AdapterItemClickListener
import com.epigra.emdb.core.listeners.MovieItemClickListener
import com.epigra.emdb.databinding.ListItemMovieBinding
import com.epigra.emdb.model.ResponseModel_Movies
import java.util.ArrayList

class Adapter_Movies(
    private val moviesList: ArrayList<ResponseModel_Movies.MovieModel>,
    private val callback: MovieItemClickListener<ResponseModel_Movies.MovieModel>
) :
    RecyclerView.Adapter<Adapter_Movies.RequestsHolder>(), AdapterItemClickListener {

    fun updateMovies(newRequesstList: ArrayList<ResponseModel_Movies.MovieModel>) {
        moviesList.clear()
        moviesList.addAll(newRequesstList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListItemMovieBinding>(
            inflater,
            R.layout.list_item_movie,
            parent,
            false
        )
        return RequestsHolder(view)
    }

    override fun getItemId(position: Int): Long {
        return moviesList[position].id!!.toLong()
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: RequestsHolder, position: Int) {
        holder.view.movieItem = moviesList[position]
        holder.view.clickListener = this
    }

    inner class RequestsHolder(var view: ListItemMovieBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onItemClicked(v: View) {
        for (roster in moviesList) {
            if (v.tag == roster) {
                callback.onMovieItemClicked(v, v.tag as ResponseModel_Movies.MovieModel)
            }
        }
    }
}