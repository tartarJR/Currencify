package com.tatar.currencify.remote.dagger

import com.tatar.currencify.dagger.app.scope.AppScope
import com.tatar.currencify.remote.api.CurrencifyApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
object ApiModule {

    @AppScope
    @Provides
    fun currencifyApi(retrofit: Retrofit): CurrencifyApi =
        retrofit.create(CurrencifyApi::class.java)
}
