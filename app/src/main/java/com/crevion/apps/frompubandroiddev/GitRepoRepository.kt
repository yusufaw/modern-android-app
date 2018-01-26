package com.crevion.apps.frompubandroiddev

import io.reactivex.Observable


/**
 * Created by yusufaw on 1/26/18.
 */

class GitRepoRepository(private val netManager: NetManager) {

    private val localDataSource = GitRepoLocalDataSource()
    private val remoteDataSource = GitRepoRemoteDataSource()

    fun getRepositories(): Observable<ArrayList<Repository>> {
        netManager.isConnectedToInternet?.let {
            if(it) {
                return remoteDataSource.getRepositories().flatMap {
                    return@flatMap localDataSource.saveRepositories(it)
                            .toSingleDefault(it)
                            .toObservable()
                }
            }
        }
        return localDataSource.getRepositories()
    }
}