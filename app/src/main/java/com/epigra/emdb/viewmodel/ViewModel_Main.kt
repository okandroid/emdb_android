package com.epigra.emdb.viewmodel

import androidx.lifecycle.MutableLiveData
import com.epigra.emdb.core.AppEmdb
import com.epigra.emdb.di.components.DaggerComponentMovies
import com.epigra.emdb.model.ResponseModel_Genres
import com.epigra.emdb.model.ResponseModel_Movies
import com.epigra.emdb.network.WebServiceApi
import com.epigra.emdb.utils.T
import com.epigra.emdb.utils.managers.AppDataCacheManager
import com.epigra.emdb.viewmodel.base.BaseViewModel
import com.gayebilisim.belsis.di.modules.ModuleApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewModel_Main : BaseViewModel() {

    @Inject
    lateinit var cache: AppDataCacheManager

    val moviesAdapterCallback by lazy { MutableLiveData<ResponseModel_Movies>() }
    val upcomingMoviesAdapterCallback by lazy { MutableLiveData<ResponseModel_Movies>() }

    init {
        DaggerComponentMovies.builder()
            .moduleApp(ModuleApp(AppEmdb.instance))
            .build()
            .injectViewModel(this)
    }

    fun getMovies(page: Int) {
        disposable.add(
            api.getMovies(T.ApiParams.API_KEY, page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : WebServiceApi.BaseCallBack<ResponseModel_Movies>() {
                    override fun onSuccessResponse(t: ResponseModel_Movies?) {
                        moviesAdapterCallback.value = t
                    }

                    override fun onError(statusCode: Int?, errorMessage: String?) {
                        finishCallback.value = false
                        loadErrorWithMessage.value = Pair(statusCode, errorMessage)
                    }
                })
        )
    }

    fun getRecommendedMovies(movieId: Int) {
        disposable.add(
            api.getRecommendedMovies(movieId, T.ApiParams.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : WebServiceApi.BaseCallBack<ResponseModel_Movies>() {
                    override fun onSuccessResponse(t: ResponseModel_Movies?) {
                        upcomingMoviesAdapterCallback.value = t
                    }

                    override fun onError(statusCode: Int?, errorMessage: String?) {
                        finishCallback.value = false
                        loadErrorWithMessage.value = Pair(statusCode, errorMessage)
                    }
                })
        )
    }

    fun getGenres() {
        disposable.add(
            api.getGenres(T.ApiParams.API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseModel_Genres>() {
                    override fun onSuccess(t: ResponseModel_Genres) {
                        cache.saveGenres(t)
                        getMovies(1)
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

    fun getGenresById(model: ResponseModel_Genres) : String {
        val sb = StringBuilder()
        for (i in 0 until model.genresArray!!.size){
            if (model.genresArray!!.size -1 == i)
                sb.append(model.genresArray!![i].name)
            else
                sb.append(model.genresArray!![i].name).append(", ")
        }
        return sb.toString()
    }
}