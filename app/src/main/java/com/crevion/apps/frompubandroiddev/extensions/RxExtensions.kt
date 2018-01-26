package com.crevion.apps.frompubandroiddev.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by yusufaw on 1/26/18.
 */

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}