package com.crevion.apps.frompubandroiddev

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField

/**
 * Created by yusufaw on 1/26/18.
 */

class MainViewModel : AndroidViewModel {

    constructor(application: Application) : super(application)
    var gitRepoRepository: GitRepoRepository = GitRepoRepository(NetManager(getApplication()))
    var isLoading = ObservableField(false)
    var repositories = MutableLiveData<ArrayList<Repository>>()

    fun loadRepositories() {
        isLoading.set(true)
        gitRepoRepository.getRepositories(object: OnRepositoryReadyCallback {
            override fun onDataReady(data: ArrayList<Repository>) {
                isLoading.set(false)
                repositories.value = data
            }
        })
    }
}