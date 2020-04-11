package com.tatar.currencify.presentation

import androidx.lifecycle.ViewModel
import com.tatar.currencify.domain.usecase.GetRatesTask

class RatesViewModel internal constructor(
    private val getRatesTask: GetRatesTask
) : ViewModel() {
}
