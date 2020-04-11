package com.tatar.currencify.data

import com.tatar.currencify.data.RemoteDataSource
import com.tatar.currencify.domain.repository.RatesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class RatesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RatesRepository {
}
