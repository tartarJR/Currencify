package com.tatar.currencify.data

import com.tatar.currencify.domain.repository.RatesRepository

class RatesRepositoryImpl constructor(
    private val remoteDataSource: RemoteDataSource
) : RatesRepository {
}
