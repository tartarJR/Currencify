package com.tatar.currencify.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tatar.currencify.CurrencifyApp
import com.tatar.currencify.R
import com.tatar.currencify.dagger.rates.component.DaggerRatesActivityComponent
import com.tatar.currencify.presentation.RatesViewModel
import javax.inject.Inject

class RatesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var ratesViewModel: RatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rates)

        provideDependencies()
        setViewModel()
    }

    private fun provideDependencies() {
        DaggerRatesActivityComponent.factory()
            .create(CurrencifyApp.appComponent(this))
            .inject(this)
    }

    private fun setViewModel() {
        ratesViewModel = ViewModelProvider(this, viewModelFactory).get(RatesViewModel::class.java)
    }
}
