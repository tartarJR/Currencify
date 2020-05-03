package com.tatar.currencify.domain.feature

data class LatestRatesEntity(
    val baseCurrency: String,
    val rates: List<RateEntity>
)

data class RateEntity(
    val currencyCode: String,
    val rate: Double
)
