package com.tatar.currencify.presentation.feature

data class CurrencyConversionResult(
    val baseCurrency: String,
    val convertedCurrencyList: List<ConvertedCurrency>
)

data class ConvertedCurrency(
    val currencyCode: String,
    val amount: Double
)
