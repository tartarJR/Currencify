package com.tatar.currencify.ui.feature

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.tatar.currencify.dagger.currency.component.DaggerCurrencyConversionComponent
import com.tatar.currencify.databinding.ActivityCurrencyConversionBinding
import com.tatar.currencify.presentation.feature.CurrencyConversionResult
import com.tatar.currencify.presentation.feature.CurrencyConversionViewModel
import com.tatar.currencify.presentation.viewmodel.EventObserver
import com.tatar.currencify.ui.util.toCurrencyFlag
import com.tatar.currencify.ui.util.toCurrencyString
import javax.inject.Inject

class CurrencyConversionActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var blinkAnim: Animation

    @Inject
    lateinit var convertedCurrenciesAdapter: ConvertedCurrenciesAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var currencyConversionViewModel: CurrencyConversionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCurrencyConversionBinding.inflate(layoutInflater)
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
        DaggerCurrencyConversionComponent.factory()
            .create(CurrencifyApp.appComponent(this))
            .inject(this)
    }

    private fun setViewModel() {
        currencyConversionViewModel = ViewModelProvider(this, viewModelFactory).get(CurrencyConversionViewModel::class.java)
    }

    private fun setupAnimation(binding: ActivityCurrencyConversionBinding) {
        blinkAnim = AnimationUtils.loadAnimation(this, R.anim.blink)

        blinkAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                setDividerColor(binding, R.color.colorAccent)
            }

            override fun onAnimationEnd(animation: Animation) {
                setDividerColor(binding, R.color.colorTextLight)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun setDividerColor(binding: ActivityCurrencyConversionBinding, color: Int) =
        binding.currencyResponderDivider.setBackgroundColor(ContextCompat.getColor(this, color))

    private fun setupInteractions(binding: ActivityCurrencyConversionBinding) {
        binding.tryAgainBtn.setOnClickListener {
            currencyConversionViewModel.onTryAgainClick()
        }

        binding.responderCurrencyContainer.setOnClickListener {
            currencyConversionViewModel.onResponderContainerClick()
        }

        binding.responderCurrencyAmountEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                currencyConversionViewModel.onAmountChange(s.toString())
            }
        })
    }

    private fun setupRecyclerview(binding: ActivityCurrencyConversionBinding) {
        with(binding) {
            convertedCurrenciesAdapter.setItemClickListener(this@CurrencyConversionActivity)
            currencyConversionRv.adapter = convertedCurrenciesAdapter

            currencyConversionRv.layoutManager = LinearLayoutManager(
                this@CurrencyConversionActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun observeState(binding: ActivityCurrencyConversionBinding) {
        currencyConversionViewModel.loadingState.observe(
            this,
            Observer { isLoading ->
                if (isLoading) {
                    toggleProgressbar(binding, View.VISIBLE)
                    toggleCurrencyConverter(binding, View.GONE)
                    toggleErrorContent(binding, View.GONE)
                } else {
                    toggleProgressbar(binding, View.GONE)
                    toggleCurrencyConverter(binding, View.VISIBLE)
                }
            })

        currencyConversionViewModel.currencyConversionResult.observe(
            this,
            Observer { currencyConversionResult ->
                setResponderCurrencyView(binding, currencyConversionResult)
                setConvertedCurrencyList(currencyConversionResult)
            })
    }

    private fun setResponderCurrencyView(
        binding: ActivityCurrencyConversionBinding,
        currencyConversionResult: CurrencyConversionResult
    ) {
        with(binding) {
            responderCurrencyIv.setImageResource(currencyConversionResult.baseCurrency.toCurrencyFlag())
            responderCurrencyAbbTv.text = currencyConversionResult.baseCurrency
            responderCurrencyNameTv.text = currencyConversionResult.baseCurrency.toCurrencyString()
        }
    }

    private fun setConvertedCurrencyList(currencyConversionResult: CurrencyConversionResult) {
        convertedCurrenciesAdapter.convertedCurrencyList = currencyConversionResult.convertedCurrencyList
    }

    private fun observeEvents(binding: ActivityCurrencyConversionBinding) {
        currencyConversionViewModel.responderClickEvent.observe(
            this,
            EventObserver { displayResponderCurrencyMessage() }
        )

        currencyConversionViewModel.animationStartEvent.observe(
            this,
            EventObserver { startBlinkingAnimation(binding) }
        )

        currencyConversionViewModel.hideKeyboardEvent.observe(
            this,
            EventObserver { hideKeyboard() }
        )

        currencyConversionViewModel.errorEvent.observe(
            this,
            EventObserver {
                toggleProgressbar(binding, View.GONE)
                toggleCurrencyConverter(binding, View.GONE)
                toggleErrorContent(binding, View.VISIBLE)
            })
    }

    private fun displayResponderCurrencyMessage() =
        Toast.makeText(this, getString(R.string.responder_currency_msg), Toast.LENGTH_LONG).show()

    private fun startBlinkingAnimation(binding: ActivityCurrencyConversionBinding) =
        binding.currencyResponderDivider.startAnimation(blinkAnim)

    private fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = this.currentFocus

        if (view == null) view = View(this)

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun toggleProgressbar(binding: ActivityCurrencyConversionBinding, visibility: Int) {
        binding.progressBar.visibility = visibility
    }

    private fun toggleCurrencyConverter(
        binding: ActivityCurrencyConversionBinding,
        visibility: Int
    ) {
        binding.responderCurrencyContainer.visibility = visibility
        binding.currencyConversionRv.visibility = visibility
        binding.currencyResponderDivider.visibility = visibility
    }

    private fun toggleErrorContent(binding: ActivityCurrencyConversionBinding, visibility: Int) {
        binding.errorTitleTv.visibility = visibility
        binding.errorTv.visibility = visibility
        binding.tryAgainBtn.visibility = visibility
    }

    override fun onItemClick(baseCurrency: String) = currencyConversionViewModel.onCurrencyConversionItemClick(baseCurrency)
}
