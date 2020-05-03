package com.tatar.currencify.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tatar.currencify.data.feature.LatestRatesData
import com.tatar.currencify.data.feature.RateData
import com.tatar.currencify.data.mapper.DataMappable

@JsonClass(generateAdapter = true)
data class LatestRatesResponse(
    @Json(name = "baseCurrency") val baseCurrency: String,
    @Json(name = "rates") val ratesByCurrency: Map<String, Double>
) : DataMappable<LatestRatesData> {
    override fun asData(): LatestRatesData {
        return LatestRatesData(
            baseCurrency,
            mapToRatesDataList()
        )
    }

    private fun mapToRatesDataList(): List<RateData> {
        return ratesByCurrency.entries.map { rateEntry ->
            RateData(
                rateEntry.key,
                rateEntry.value
            )
        }
    }
}
