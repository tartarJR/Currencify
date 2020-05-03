package com.tatar.currencify.data.dagger

import com.tatar.currencify.data.feature.RatesRepositoryImpl
import com.tatar.currencify.domain.repository.RatesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindRatesRepository(ratesRepository: RatesRepositoryImpl): RatesRepository
}
