package com.tatar.currencify.remote.api

import com.tatar.currencify.remote.model.LatestRatesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencifyApi {
    @GET("latest")
    fun getLatestRates(@Query("base") baseCurrency: String): Single<LatestRatesResponse>
}
