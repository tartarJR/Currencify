package com.tatar.currencify.ui.util

import com.tatar.currencify.R

fun String.toCurrencyString(): String {
    return when (this) {
        CURRENCY_CODE_AUD -> "Australian Dollar"
        CURRENCY_CODE_BGN -> "Bulgarian Lev"
        CURRENCY_CODE_BRL -> "Brazilian Real"
        CURRENCY_CODE_CAD -> "Canadian Dollar"
        CURRENCY_CODE_CHF -> "Swiss Franc"
        CURRENCY_CODE_CNY -> "Chinese Yuan"
        CURRENCY_CODE_CZK -> "Czech Koruna"
        CURRENCY_CODE_DKK -> "Danish Krone"
        CURRENCY_CODE_EUR -> "Euro"
        CURRENCY_CODE_GBP -> " British Pound Sterling"
        CURRENCY_CODE_HKD -> "Hong Kong Dollar"
        CURRENCY_CODE_HRK -> "Croatian Kuna"
        CURRENCY_CODE_HUF -> "Hungarian Forint"
        CURRENCY_CODE_IDR -> "Indonesian Rupiah"
        CURRENCY_CODE_ILS -> "Israeli New Shekel"
        CURRENCY_CODE_INR -> "Indian Rupee"
        CURRENCY_CODE_ISK -> "Iceland Krona"
        CURRENCY_CODE_JPY -> "Japanese yen"
        CURRENCY_CODE_KRW -> "South Korean Won"
        CURRENCY_CODE_MXN -> "Mexican Peso"
        CURRENCY_CODE_MYR -> "Malaysian ringgit"
        CURRENCY_CODE_NOK -> "Norwegian Krone"
        CURRENCY_CODE_NZD -> "New Zealand Dollar"
        CURRENCY_CODE_PHP -> "Philippine Peso"
        CURRENCY_CODE_PLN -> "Polish Zloty"
        CURRENCY_CODE_RON -> "Romanian New Leu"
        CURRENCY_CODE_RUB -> "Russian Ruble"
        CURRENCY_CODE_SEK -> "Swedish Krona"
        CURRENCY_CODE_SGD -> "Singapore Dollar"
        CURRENCY_CODE_THB -> "Thai Baht"
        CURRENCY_CODE_USD -> "United States Dollar"
        CURRENCY_CODE_ZAR -> "South African Rand"
        else -> throw IllegalArgumentException()
    }
}

fun String.toCurrencyFlag(): Int {
    return when (this) {
        CURRENCY_CODE_AUD -> R.drawable.ic_australia
        CURRENCY_CODE_BGN -> R.drawable.ic_bulgaria
        CURRENCY_CODE_BRL -> R.drawable.ic_brazil
        CURRENCY_CODE_CAD -> R.drawable.ic_canada
        CURRENCY_CODE_CHF -> R.drawable.ic_switzerland
        CURRENCY_CODE_CNY -> R.drawable.ic_china
        CURRENCY_CODE_CZK -> R.drawable.ic_czech_republic
        CURRENCY_CODE_DKK -> R.drawable.ic_denmark
        CURRENCY_CODE_EUR -> R.drawable.ic_european_union
        CURRENCY_CODE_GBP -> R.drawable.ic_united_kingdom
        CURRENCY_CODE_HKD -> R.drawable.ic_hong_kong
        CURRENCY_CODE_HRK -> R.drawable.ic_croatia
        CURRENCY_CODE_HUF -> R.drawable.ic_hungary
        CURRENCY_CODE_IDR -> R.drawable.ic_indonesia
        CURRENCY_CODE_ILS -> R.drawable.ic_israel
        CURRENCY_CODE_INR -> R.drawable.ic_india
        CURRENCY_CODE_ISK -> R.drawable.ic_iceland
        CURRENCY_CODE_JPY -> R.drawable.ic_japan
        CURRENCY_CODE_KRW -> R.drawable.ic_south_korea
        CURRENCY_CODE_MXN -> R.drawable.ic_mexico
        CURRENCY_CODE_MYR -> R.drawable.ic_malaysia
        CURRENCY_CODE_NOK -> R.drawable.ic_norway
        CURRENCY_CODE_NZD -> R.drawable.ic_new_zealand
        CURRENCY_CODE_PHP -> R.drawable.ic_philippines
        CURRENCY_CODE_PLN -> R.drawable.ic_poland
        CURRENCY_CODE_RON -> R.drawable.ic_romania
        CURRENCY_CODE_RUB -> R.drawable.ic_russia
        CURRENCY_CODE_SEK -> R.drawable.ic_sweden
        CURRENCY_CODE_SGD -> R.drawable.ic_singapore
        CURRENCY_CODE_THB -> R.drawable.ic_thailand
        CURRENCY_CODE_USD -> R.drawable.ic_united_states
        CURRENCY_CODE_ZAR -> R.drawable.ic_south_africa
        else -> throw IllegalArgumentException()
    }
}

private const val CURRENCY_CODE_AUD = "AUD"
private const val CURRENCY_CODE_BGN = "BGN"
private const val CURRENCY_CODE_BRL = "BRL"
private const val CURRENCY_CODE_CAD = "CAD"
private const val CURRENCY_CODE_CHF = "CHF"
private const val CURRENCY_CODE_CNY = "CNY"
private const val CURRENCY_CODE_CZK = "CZK"
private const val CURRENCY_CODE_DKK = "DKK"
private const val CURRENCY_CODE_EUR = "EUR"
private const val CURRENCY_CODE_GBP = "GBP"
private const val CURRENCY_CODE_HKD = "HKD"
private const val CURRENCY_CODE_HRK = "HRK"
private const val CURRENCY_CODE_HUF = "HUF"
private const val CURRENCY_CODE_IDR = "IDR"
private const val CURRENCY_CODE_ILS = "ILS"
private const val CURRENCY_CODE_INR = "INR"
private const val CURRENCY_CODE_ISK = "ISK"
private const val CURRENCY_CODE_JPY = "JPY"
private const val CURRENCY_CODE_KRW = "KRW"
private const val CURRENCY_CODE_MXN = "MXN"
private const val CURRENCY_CODE_MYR = "MYR"
private const val CURRENCY_CODE_NOK = "NOK"
private const val CURRENCY_CODE_NZD = "NZD"
private const val CURRENCY_CODE_PHP = "PHP"
private const val CURRENCY_CODE_PLN = "PLN"
private const val CURRENCY_CODE_RON = "RON"
private const val CURRENCY_CODE_RUB = "RUB"
private const val CURRENCY_CODE_SEK = "SEK"
private const val CURRENCY_CODE_SGD = "SGD"
private const val CURRENCY_CODE_THB = "THB"
private const val CURRENCY_CODE_USD = "USD"
private const val CURRENCY_CODE_ZAR = "ZAR"

