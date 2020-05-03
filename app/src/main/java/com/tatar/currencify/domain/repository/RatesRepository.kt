package com.tatar.currencify.domain.repository

import com.tatar.currencify.domain.feature.LatestRatesEntity
import io.reactivex.rxjava3.core.Single

interface RatesRepository {
    fun getLatestRates(baseCurrency: String): Single<LatestRatesEntity>
}
