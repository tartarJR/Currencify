package com.tatar.currencify.data.source

import com.tatar.currencify.data.feature.LatestRatesData
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {
    fun getLatestRates(baseCurrency: String): Single<LatestRatesData>
}
