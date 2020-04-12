package com.tatar.currencify.remote.source

import com.tatar.currencify.dagger.app.scope.AppScope
import com.tatar.currencify.data.RemoteDataSource
import com.tatar.currencify.remote.api.CurrencifyApi
import javax.inject.Inject

@AppScope
class RemoteDataSourceImpl @Inject constructor(
    private val currencifyApi: CurrencifyApi
) : RemoteDataSource {
}
