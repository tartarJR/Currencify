package com.tatar.currencify.presentation

import androidx.lifecycle.ViewModel
import com.tatar.currencify.domain.usecase.GetRatesTask
import javax.inject.Inject

class RatesViewModel @Inject internal constructor(
    private val getRatesTask: GetRatesTask
) : ViewModel() {
}
