package com.epigra.emdb.viewmodel

import com.epigra.emdb.core.AppEmdb
import com.epigra.emdb.di.components.DaggerComponentMovies
import com.epigra.emdb.di.components.DaggerComponentSplash
import com.epigra.emdb.model.ResponseModel_Token
import com.epigra.emdb.utils.managers.AppDataCacheManager
import com.epigra.emdb.viewmodel.base.BaseViewModel
import com.gayebilisim.belsis.di.modules.ModuleApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewModel_Splash : BaseViewModel() {

    @Inject
    lateinit var cache: AppDataCacheManager

    init {
       DaggerComponentSplash.builder()
            .moduleApp(ModuleApp(AppEmdb.instance))
            .build()
            .injectViewModel(this)
    }

    fun callTokenService() {
        disposable.add(
            api.getToken()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseModel_Token>() {
                    override fun onSuccess(t: ResponseModel_Token) {
                        // here we can store ws token
                        finishCallback.value = true
                    }

                    override fun onError(e: Throwable) {
                        loadiong.value = false
                        finishCallback.value = false
                    }
                })
        )
    }


}