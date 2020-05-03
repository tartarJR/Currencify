package com.tatar.currencify.dagger.app.module

import com.tatar.currencify.dagger.app.scope.AppScope
import com.tatar.currencify.domain.base.JobSchedulers
import com.tatar.currencify.ui.util.RxJavaSchedulers
import dagger.Binds
import dagger.Module

@Module
abstract class RxSchedulerModule {

    @Binds
    @AppScope
    abstract fun bindJobSchedulers(schedulers: RxJavaSchedulers): JobSchedulers
}
