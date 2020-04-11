package com.tatar.currencify.domain.usecase

import com.tatar.currencify.domain.base.SingleUseCase
import com.tatar.currencify.domain.repository.RatesRepository
import dagger.Reusable
import javax.inject.Inject

@Reusable
class GetRatesTask @Inject constructor(
    private val ratesRepository: RatesRepository
) : SingleUseCase() {
}
