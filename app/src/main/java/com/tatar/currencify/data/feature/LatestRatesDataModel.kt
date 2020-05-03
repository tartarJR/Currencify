package com.tatar.currencify.data.feature

import com.tatar.currencify.domain.feature.LatestRatesEntity
import com.tatar.currencify.domain.feature.RateEntity
import com.tatar.currencify.domain.mapper.DomainMappable

data class LatestRatesData(
    val baseCurrency: String,
    val rateDataList: List<RateData>
) : DomainMappable<LatestRatesEntity> {
    override fun asDomain(): LatestRatesEntity {
        return LatestRatesEntity(
            baseCurrency,
            mapToRates()
        )
    }

    private fun mapToRates(): List<RateEntity> {
        return rateDataList.map { rateData ->
            rateData.asDomain()
        }
    }
}

data class RateData(
    val currencyCode: String,
    val rate: Double
) : DomainMappable<RateEntity> {
    override fun asDomain(): RateEntity {
        return RateEntity(currencyCode, rate)
    }
}
