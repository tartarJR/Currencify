package com.tatar.currencify.remote.source

import com.tatar.currencify.dagger.app.scope.AppScope
import com.tatar.currencify.data.feature.LatestRatesData
import com.tatar.currencify.data.source.RemoteDataSource
import com.tatar.currencify.remote.api.CurrencifyApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@AppScope
class RemoteDataSourceImpl @Inject constructor(
    private val currencifyApi: CurrencifyApi
) : RemoteDataSource {
    override fun getLatestRates(baseCurrency: String): Single<LatestRatesData> {
        return currencifyApi.getLatestRates(baseCurrency)
            .map { latestRatesResponse -> latestRatesResponse.asData() }
    }
}
