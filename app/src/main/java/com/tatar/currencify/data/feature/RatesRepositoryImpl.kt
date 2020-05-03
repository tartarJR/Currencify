package com.tatar.currencify.data.feature

import com.tatar.currencify.data.source.RemoteDataSource
import com.tatar.currencify.domain.feature.LatestRatesEntity
import com.tatar.currencify.domain.repository.RatesRepository
import dagger.Reusable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@Reusable
class RatesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RatesRepository {
    override fun getLatestRates(baseCurrency: String): Single<LatestRatesEntity> {
        return remoteDataSource.getLatestRates(baseCurrency)
            .map { latestRatesData ->
                latestRatesData.asDomain()
            }
    }
}
