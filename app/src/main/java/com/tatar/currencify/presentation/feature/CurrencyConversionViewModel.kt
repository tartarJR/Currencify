package com.tatar.currencify.presentation.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatar.currencify.domain.feature.GetRatesTask
import com.tatar.currencify.domain.feature.LatestRatesEntity
import com.tatar.currencify.presentation.util.addTo
import com.tatar.currencify.presentation.viewmodel.Event
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Flowables
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class CurrencyConversionViewModel @Inject internal constructor(
    private val getRatesTask: GetRatesTask
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _currencyConversionResult = MutableLiveData<CurrencyConversionResult>()
    val currencyConversionResult: LiveData<CurrencyConversionResult> get() = _currencyConversionResult

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _responderClickEvent = MutableLiveData<Event<Unit>>()
    val responderClickEvent: LiveData<Event<Unit>> get() = _responderClickEvent

    private val _animationStartEvent = MutableLiveData<Event<Unit>>()
    val animationStartEvent: LiveData<Event<Unit>> get() = _animationStartEvent

    private val _hideKeyboardEvent = MutableLiveData<Event<Unit>>()
    val hideKeyboardEvent: LiveData<Event<Unit>> get() = _hideKeyboardEvent

    private val _errorEvent = MutableLiveData<Event<Unit>>()
    val errorEvent: LiveData<Event<Unit>> get() = _errorEvent

    private val amountSubject: BehaviorSubject<Double> = BehaviorSubject.createDefault(INITIAL_BASE_CURRENCY_AMOUNT)

    init {
        startCurrencyTransformationFlow(INITIAL_BASE_CURRENCY)
    }

    fun onCurrencyConversionItemClick(baseCurrency: String) {
        hideKeyboard()
        stopCurrencyTransformationFlow()
        startCurrencyTransformationFlow(baseCurrency)
    }

    private fun stopCurrencyTransformationFlow() {
        if (disposables.size() > 0) disposables.clear()
    }

    private fun startCurrencyTransformationFlow(baseCurrency: String) {
        Flowables.combineLatest(
            getRatesTask.invoke(baseCurrency),
            amountSubject.toFlowable(BackpressureStrategy.LATEST)
        ) { latestRatesEntity, amount -> mapToCurrencyConversionResult(latestRatesEntity, amount) }
            .doOnSubscribe { onNewFlowRequest() }
            .subscribe(
                { currencyConversionResult -> onSuccess(currencyConversionResult) },
                { onError() }
            ).addTo(disposables)
    }

    private fun mapToCurrencyConversionResult(
        latestRatesEntity: LatestRatesEntity,
        amount: Double
    ): CurrencyConversionResult {
        return CurrencyConversionResult(
            baseCurrency = latestRatesEntity.baseCurrency,
            convertedCurrencyList = latestRatesEntity.rateEntityList.map {
                ConvertedCurrency(it.currencyCode, it.rate * amount)
            }
        )
    }

    private fun onNewFlowRequest() = _loadingState.postValue(true)

    private fun onSuccess(currencyConversionResult: CurrencyConversionResult) {
        if (isNewFlow(loadingState.value)) {
            _loadingState.postValue(false)
            _animationStartEvent.postValue(Event(Unit))
        }

        _currencyConversionResult.postValue(currencyConversionResult)
    }

    private fun isNewFlow(isLoading: Boolean?) = isLoading == true

    private fun onError() {
        _errorEvent.postValue(Event(Unit))
        hideKeyboard()
    }

    private fun hideKeyboard() {
        _hideKeyboardEvent.value = (Event(Unit))
    }

    fun onAmountChange(amountText: CharSequence) {
        if (amountText.isNotEmpty()) amountSubject.onNext(amountText.toString().toDouble())
        else amountSubject.onNext(INITIAL_BASE_CURRENCY_AMOUNT)
    }

    fun onResponderContainerClick() = _responderClickEvent.postValue(Event(Unit))

    fun onTryAgainClick() {
        val lastBaseCurrency = _currencyConversionResult.value?.baseCurrency ?: INITIAL_BASE_CURRENCY
        startCurrencyTransformationFlow(lastBaseCurrency)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        private const val INITIAL_BASE_CURRENCY = "EUR"
        private const val INITIAL_BASE_CURRENCY_AMOUNT = 0.0
    }
}
