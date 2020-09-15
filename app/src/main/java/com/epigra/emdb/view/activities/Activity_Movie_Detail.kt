package com.epigra.emdb.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.epigra.emdb.R
import com.epigra.emdb.core.BaseActivity
import com.epigra.emdb.core.listeners.MovieItemClickListener
import com.epigra.emdb.databinding.ActivityMovieDetailBinding
import com.epigra.emdb.model.ResponseModel_Movies
import com.epigra.emdb.utils.T
import com.epigra.emdb.view.adapters.Adapter_Recommended_Movies
import com.epigra.emdb.viewmodel.ViewModel_Main
import kotlinx.android.synthetic.main.activity_movie_detail.*
import org.jetbrains.anko.toast

class Activity_Movie_Detail : BaseActivity(),
    MovieItemClickListener<ResponseModel_Movies.MovieModel> {
    private lateinit var dataBinding: ActivityMovieDetailBinding
    private lateinit var viewModel: ViewModel_Main
    private var recommendedMoviesAdapter = Adapter_Recommended_Movies(arrayListOf())
    var totalRecommendedMoviesArray = arrayListOf<ResponseModel_Movies.MovieModel>()

    var movieModel: ResponseModel_Movies.MovieModel? = null

    private val loadinLiveDataObserver = Observer<Boolean> { isLoading ->

    }

    private var finishLiveDataObserver = Observer<ResponseModel_Movies> { movies ->
        movies.results?.let { totalRecommendedMoviesArray.addAll(it) }

        if (totalRecommendedMoviesArray.size == 0) {
            recommendation_layout.visibility = View.GONE
        } else {
            recommendation_layout.visibility = View.VISIBLE
            recommendedMoviesAdapter.updateRecommendedMovies(totalRecommendedMoviesArray)
            recommended_pager.adapter = recommendedMoviesAdapter
        }
    }

    override fun getAnalyticsScreenName(): String? = javaClass.simpleName

    override fun getContentLayoutID(): Int = R.layout.activity_movie_detail

    override fun findUI() {

    }

    override fun setupUI(savedInstanceState: Bundle?) {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        movieModel =
            intent.getSerializableExtra(T.BundleExtra.MOVIE_DATA) as ResponseModel_Movies.MovieModel
        viewModel = ViewModelProvider(this).get(ViewModel_Main::class.java)

        dataBinding.movieItem = movieModel
        dataBinding.genres = mDataCache.getGenres()
        dataBinding.viewModelMain = viewModel

        viewModel.loadiong.observe(this, loadinLiveDataObserver)
        viewModel.upcomingMoviesAdapterCallback.observe(this, finishLiveDataObserver)

        movieModel!!.id?.let { viewModel.getRecommendedMovies(movieId = it) }

        val recyclerViewInstance =
            recommended_pager.children.first { it is RecyclerView } as RecyclerView

        recyclerViewInstance.also {
            it.setPadding(86, 0, 86, 0)
            it.clipToPadding = false
        }

        detail_vote_button.setOnClickListener {
            toast(getString(R.string.txt_limited_action))
        }
    }

    override fun onActivityResultFetched(requestCode: Int, resultCode: Int, data: Intent?) {

    }

    override fun onPermissionResultFetched(
        requestCode: Int,
        grantedPermissions: Array<String>,
        deniedPermissions: Array<String>
    ) {

    }

    override fun <E> onBusDataFetched(data: E) {

    }

    override fun onMovieItemClicked(v: View, data: ResponseModel_Movies.MovieModel) {

    }
}