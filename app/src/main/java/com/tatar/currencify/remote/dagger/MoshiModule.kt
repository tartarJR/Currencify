package com.tatar.currencify.remote.dagger

import com.squareup.moshi.Moshi
import com.tatar.currencify.dagger.app.scope.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object MoshiModule {

    @AppScope
    @Provides
    fun moshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    @AppScope
    @Provides
    fun moshi(): Moshi = Moshi.Builder().build()
}
