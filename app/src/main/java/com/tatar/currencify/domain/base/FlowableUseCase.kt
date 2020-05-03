package com.tatar.currencify.domain.base

import io.reactivex.rxjava3.core.Flowable

abstract class FlowableUseCase<in Input, Output>(
    private val schedulers: JobSchedulers
) {
    protected abstract fun execute(input: Input): Flowable<Output>

    operator fun invoke(input: Input): Flowable<Output> {
        return execute(input)
            .subscribeOn(schedulers.backgroundScheduler)
            .observeOn(schedulers.mainScheduler)
    }
}
