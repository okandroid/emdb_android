package com.epigra.emdb.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.epigra.emdb.R
import com.epigra.emdb.core.BaseActivity
import com.epigra.emdb.core.listeners.MovieItemClickListener
import com.epigra.emdb.databinding.ActivityMainBinding
import com.epigra.emdb.databinding.ActivityMovieDetailBinding
import com.epigra.emdb.model.ResponseModel_Movies
import com.epigra.emdb.utils.T
import com.epigra.emdb.utils.behavior.RecyclerViewScrollListener
import com.epigra.emdb.viewmodel.ViewModel_Main
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.smartral.dating.sevenful.view.adapters.Adapter_Movies
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivityForResult
import java.util.*


class Activity_Main : BaseActivity(), MovieItemClickListener<ResponseModel_Movies.MovieModel>,
    RecyclerViewScrollListener.ScrollCallback, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var viewModel: ViewModel_Main

    private var moviesAdapter = Adapter_Movies(arrayListOf(), this)
    private val mScrollListener by lazy { RecyclerViewScrollListener(this) }
    var page = 1
    var moviesCount = 0
    var totalMoviesArray = arrayListOf<ResponseModel_Movies.MovieModel>()
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    private val loadinLiveDataObserver = Observer<Boolean> { isLoading ->

    }

    private var finishLiveDataObserver = Observer<ResponseModel_Movies> { movies ->
        movies.results?.let { totalMoviesArray.addAll(it) }
        swipeRefreshLayout?.isRefreshing = false
        if (totalMoviesArray.size == 0) {
            empty_movies_view.visibility = View.VISIBLE
        } else {
            empty_movies_view.visibility = View.GONE
            moviesAdapter.updateMovies(totalMoviesArray)
            moviesCount = totalMoviesArray.size
            page += 1
        }
    }

    override fun getAnalyticsScreenName(): String? = javaClass.simpleName

    override fun getContentLayoutID(): Int = R.layout.activity_main

    override fun findUI() {
        swipeRefreshLayout = find(R.id.pull_to_refresh)
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.activityMain = this
        viewModel = ViewModelProvider(this).get(ViewModel_Main::class.java)
        viewModel.loadiong.observe(this, loadinLiveDataObserver)
        viewModel.moviesAdapterCallback.observe(this, finishLiveDataObserver)
        swipeRefreshLayout?.setOnRefreshListener(this)

        if (mDataCache.getGenres() != null)
            viewModel.getMovies(page)
        else
            viewModel.getGenres()

        moviesAdapter.setHasStableIds(true)
        movies_recyclerview.apply {
            layoutManager = GridLayoutManager(
                context,
                3,
                LinearLayoutManager.VERTICAL,
                false
            ) as RecyclerView.LayoutManager?
            adapter = moviesAdapter
            addOnScrollListener(mScrollListener)
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
        startActivityForResult<Activity_Movie_Detail>(
            T.RequestCodes.REQUEST_MOVIE_DETAIL_ACTION,
            T.BundleExtra.MOVIE_DATA to data
        )
    }

    override fun onScrollCompleted(firstVisibleItem: Int, isLoadingMoreData: Boolean) {
        if (totalMoviesArray.size >= 20) {
            viewModel.getMovies(page)
        }
    }

    fun showBottomSheetDialog() {
        val view: View =
            layoutInflater.inflate(R.layout.dialog_bottom_sheet, null)

        val dialog = BottomSheetDialog(this)

        val sortByRating: LinearLayout = view.find(R.id.button_sort_by_rating)
        val sortByPopularity: LinearLayout = view.find(R.id.button_sort_by_popularity)

        sortByPopularity.setOnClickListener {
            val sortedMovies: List<ResponseModel_Movies.MovieModel> = totalMoviesArray.sortedBy {
                it.popularity
            }

            totalMoviesArray = ArrayList(sortedMovies)
            moviesAdapter.updateMovies(totalMoviesArray)
            dialog.dismiss()
        }

        sortByRating.setOnClickListener {
            val sortedMovies: List<ResponseModel_Movies.MovieModel> = totalMoviesArray.sortedBy {
                it.vote_average
            }

            totalMoviesArray = ArrayList(sortedMovies)
            moviesAdapter.updateMovies(totalMoviesArray)
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.show()
    }

    override fun onRefresh() {
        viewModel.getMovies(1)
    }
}