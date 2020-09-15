package com.epigra.emdb.viewmodel.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epigra.emdb.network.WebServiceDelegate
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    val loadErrorWithMessage by lazy { MutableLiveData<Pair<Int?, String?>>() }
    val loadiong by lazy { MutableLiveData<Boolean>() }
    val finishCallback by lazy { MutableLiveData<Boolean>() }

    val disposable = CompositeDisposable()

    @Inject
    lateinit var api: WebServiceDelegate

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}