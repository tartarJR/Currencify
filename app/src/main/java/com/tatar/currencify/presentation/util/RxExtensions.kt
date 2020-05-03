package com.tatar.currencify.presentation.util

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.addTo(disposables: CompositeDisposable): Disposable =
    apply { disposables.add(this) }
