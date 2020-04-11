package com.tatar.currencify.domain.usecase

import com.tatar.currencify.domain.base.SingleUseCase
import com.tatar.currencify.domain.repository.RatesRepository

class GetRatesTask constructor(
    private val ratesRepository: RatesRepository
) : SingleUseCase() {
}
