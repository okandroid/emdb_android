package com.epigra.emdb.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.epigra.emdb.R
import com.epigra.emdb.databinding.ListItemRecommendedMovieBinding
import com.epigra.emdb.model.ResponseModel_Movies
import java.util.*

class Adapter_Recommended_Movies(private val recommendedMoviesList: ArrayList<ResponseModel_Movies.MovieModel>
) :
    RecyclerView.Adapter<Adapter_Recommended_Movies.RequestsHolder>(){

    fun updateRecommendedMovies(newRequesstList: ArrayList<ResponseModel_Movies.MovieModel>) {
        recommendedMoviesList.clear()
        recommendedMoviesList.addAll(newRequesstList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListItemRecommendedMovieBinding>(
            inflater,
            R.layout.list_item_recommended_movie,
            parent,
            false
        )
        return RequestsHolder(view)
    }

    override fun getItemCount(): Int = recommendedMoviesList.size

    override fun onBindViewHolder(holder: RequestsHolder, position: Int) {
        holder.view.movieItem = recommendedMoviesList.get(position)
    }

    inner class RequestsHolder(var view: ListItemRecommendedMovieBinding) :
        RecyclerView.ViewHolder(view.root)
}