package com.crevion.apps.frompubandroiddev

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.crevion.apps.frompubandroiddev.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by yusufaw on 1/26/18.
 */

class MainViewModel : AndroidViewModel {

    constructor(application: Application) : super(application)

    var gitRepoRepository: GitRepoRepository = GitRepoRepository(NetManager(getApplication()))
    var isLoading = ObservableField(false)
    var repositories = MutableLiveData<ArrayList<Repository>>()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadRepositories() {
        isLoading.set(true)
        compositeDisposable += gitRepoRepository
                .getRepositories()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ArrayList<Repository>>() {
                    override fun onError(e: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(t: ArrayList<Repository>) {
                        repositories.value = t
                    }

                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}