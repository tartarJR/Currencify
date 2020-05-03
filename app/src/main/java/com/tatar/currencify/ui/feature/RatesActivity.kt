package com.tatar.currencify.ui.feature

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tatar.currencify.CurrencifyApp
import com.tatar.currencify.R
import com.tatar.currencify.dagger.rates.component.DaggerRatesActivityComponent
import com.tatar.currencify.databinding.ActivityRatesBinding
import com.tatar.currencify.domain.feature.LatestRatesEntity
import com.tatar.currencify.presentation.RatesViewModel
import com.tatar.currencify.presentation.viewmodel.EventObserver
import com.tatar.currencify.ui.util.toCurrencyFlag
import com.tatar.currencify.ui.util.toCurrencyString
import javax.inject.Inject

class RatesActivity : AppCompatActivity(), ItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var ratesAdapter: RatesAdapter

    private lateinit var ratesViewModel: RatesViewModel

    private lateinit var blinkAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        provideDependencies()
        setViewModel()
        setupAnimation(binding)
        setupInteractions(binding)
        setupRecyclerview(binding)
        observeState(binding)
        observeEvents(binding)
    }

    private fun provideDependencies() {
        DaggerRatesActivityComponent.factory()
            .create(CurrencifyApp.appComponent(this))
            .inject(this)
    }

    private fun setViewModel() {
        ratesViewModel = ViewModelProvider(this, viewModelFactory).get(RatesViewModel::class.java)
    }

    private fun setupAnimation(binding: ActivityRatesBinding) {
        blinkAnim = AnimationUtils.loadAnimation(this, R.anim.blink)

        blinkAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                binding.currencyResponderDivider.setBackgroundColor(
                    ContextCompat.getColor(
                        this@RatesActivity,
                        R.color.colorAccent
                    )
                )
            }

            override fun onAnimationEnd(animation: Animation) {
                binding.currencyResponderDivider.setBackgroundColor(
                    ContextCompat.getColor(
                        this@RatesActivity,
                        R.color.colorTextLight
                    )
                )
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun setupInteractions(binding: ActivityRatesBinding) {
        binding.tryAgainBtn.setOnClickListener {
            ratesViewModel.onTryAgainClick()
        }

        binding.responderCurrencyContainer.setOnClickListener {
            ratesViewModel.onResponderContainerClick()
        }
    }

    private fun setupRecyclerview(binding: ActivityRatesBinding) {
        with(binding) {
            ratesAdapter.setItemClickListener(this@RatesActivity)
            ratesRv.adapter = ratesAdapter

            ratesRv.layoutManager = LinearLayoutManager(
                this@RatesActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun observeState(binding: ActivityRatesBinding) {
        ratesViewModel.loadingState.observe(
            this,
            Observer { isLoading ->
                if (isLoading) {
                    displayLoadingIndicator(binding)
                    hideRatesContent(binding)
                    hideErrorContent(binding)
                } else {
                    hideLoadingIndicator(binding)
                    displayRatesContent(binding)
                }
            })

        ratesViewModel.latestRatesEntity.observe(
            this,
            Observer { latestRatesEntity ->
                setResponderCurrencyView(binding, latestRatesEntity)
                setRatesList(latestRatesEntity)
            })
    }

    private fun setResponderCurrencyView(
        binding: ActivityRatesBinding,
        latestRatesEntity: LatestRatesEntity
    ) {
        with(binding) {
            responderCurrencyIv.setImageResource(latestRatesEntity.baseCurrency.toCurrencyFlag())
            responderCurrencyAbbTv.text = latestRatesEntity.baseCurrency
            responderCurrencyNameTv.text = latestRatesEntity.baseCurrency.toCurrencyString()
        }
    }

    private fun setRatesList(latestRatesEntity: LatestRatesEntity) {
        ratesAdapter.rates = latestRatesEntity.rates
    }

    private fun observeEvents(binding: ActivityRatesBinding) {
        ratesViewModel.responderClickEvent.observe(
            this,
            EventObserver { displayResponderCurrencyMessage() }
        )

        ratesViewModel.animationEvent.observe(
            this,
            EventObserver { startBlinkingAnimation(binding) }
        )

        ratesViewModel.hideKeyboardEvent.observe(
            this,
            EventObserver { hideKeyboard() }
        )

        ratesViewModel.errorEvent.observe(
            this,
            EventObserver {
                hideLoadingIndicator(binding)
                hideRatesContent(binding)
                displayErrorContent(binding)
            })
    }

    private fun displayResponderCurrencyMessage() {
        Toast.makeText(this, getString(R.string.responder_currency_msg), Toast.LENGTH_LONG).show()
    }

    private fun startBlinkingAnimation(binding: ActivityRatesBinding) {
        binding.currencyResponderDivider.startAnimation(blinkAnim)
    }

    private fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.currentFocus

        if (view == null) view = View(this)

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun displayLoadingIndicator(binding: ActivityRatesBinding) {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator(binding: ActivityRatesBinding) {
        binding.progressBar.visibility = View.GONE
    }

    private fun displayRatesContent(binding: ActivityRatesBinding) {
        binding.responderCurrencyContainer.visibility = View.VISIBLE
        binding.ratesRv.visibility = View.VISIBLE
        binding.currencyResponderDivider.visibility = View.VISIBLE
    }

    private fun hideRatesContent(binding: ActivityRatesBinding) {
        binding.responderCurrencyContainer.visibility = View.GONE
        binding.ratesRv.visibility = View.GONE
        binding.currencyResponderDivider.visibility = View.GONE
    }

    private fun displayErrorContent(binding: ActivityRatesBinding) {
        binding.errorTitleTv.visibility = View.VISIBLE
        binding.errorTv.visibility = View.VISIBLE
        binding.tryAgainBtn.visibility = View.VISIBLE
    }

    private fun hideErrorContent(binding: ActivityRatesBinding) {
        binding.errorTitleTv.visibility = View.GONE
        binding.errorTv.visibility = View.GONE
        binding.tryAgainBtn.visibility = View.GONE
    }

    override fun onItemClick(newBaseCurrency: String) =
        ratesViewModel.onRatesItemClick(newBaseCurrency)
}
