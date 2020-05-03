package com.tatar.currencify.ui.util

import com.tatar.currencify.dagger.app.scope.AppScope
import com.tatar.currencify.domain.base.JobSchedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@AppScope
class RxJavaSchedulers @Inject constructor() : JobSchedulers {
    override val backgroundScheduler: Scheduler
        get() = Schedulers.io()
    override val mainScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}
