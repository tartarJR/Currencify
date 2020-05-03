package com.tatar.currencify.domain.feature

import com.tatar.currencify.domain.base.FlowableUseCase
import com.tatar.currencify.domain.base.JobSchedulers
import com.tatar.currencify.domain.repository.RatesRepository
import dagger.Reusable
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Reusable
class GetRatesTask @Inject constructor(
    jobSchedulers: JobSchedulers,
    private val ratesRepository: RatesRepository
) : FlowableUseCase<String, LatestRatesEntity>(jobSchedulers) {
    override fun execute(input: String): Flowable<LatestRatesEntity> {
        return Flowable.interval(INTERVAL_IN_SECONDS, TimeUnit.SECONDS)
            .flatMap { ratesRepository.getLatestRates(input).toFlowable() }
    }

    companion object {
        private const val INTERVAL_IN_SECONDS = 1L
    }
}
