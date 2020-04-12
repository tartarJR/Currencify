package com.tatar.currencify.remote.dagger

import com.tatar.currencify.dagger.app.scope.AppScope
import com.tatar.currencify.remote.util.Constants
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module(includes = [MoshiModule::class])
object NetworkModule {

    @AppScope
    @Provides
    fun retrofit(
        okHttpClient: OkHttpClient,
        rxJava2CallAdapterFactory: RxJava3CallAdapterFactory,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(okHttpClient)
            .baseUrl(Constants.API_BASE_URL)
            .build()
    }

    @AppScope
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.API_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @AppScope
    @Provides
    fun rxJava2CallAdapterFactory(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory.create()
}
