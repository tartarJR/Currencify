package com.tatar.currencify.domain.base

import io.reactivex.rxjava3.core.Scheduler

interface JobSchedulers {
    val backgroundScheduler: Scheduler
    val mainScheduler: Scheduler
}
