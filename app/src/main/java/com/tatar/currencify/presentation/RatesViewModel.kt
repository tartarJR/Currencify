package com.tatar.currencify.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tatar.currencify.domain.feature.GetRatesTask
import com.tatar.currencify.domain.feature.LatestRatesEntity
import com.tatar.currencify.presentation.util.addTo
import com.tatar.currencify.presentation.viewmodel.Event
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class RatesViewModel @Inject internal constructor(
    private val getRatesTask: GetRatesTask
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _latestRatesEntity = MutableLiveData<LatestRatesEntity>()
    val latestRatesEntity: LiveData<LatestRatesEntity> get() = _latestRatesEntity

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _responderClickEvent = MutableLiveData<Event<Unit>>()
    val responderClickEvent: LiveData<Event<Unit>> get() = _responderClickEvent

    private val _animationEvent = MutableLiveData<Event<Unit>>()
    val animationEvent: LiveData<Event<Unit>> get() = _animationEvent

    private val _hideKeyboardEvent = MutableLiveData<Event<Unit>>()
    val hideKeyboardEvent: LiveData<Event<Unit>> get() = _hideKeyboardEvent

    private val _errorEvent = MutableLiveData<Event<Unit>>()
    val errorEvent: LiveData<Event<Unit>> get() = _errorEvent

    init {
        startRatesFlow(INITIAL_BASE_CURRENCY)
    }

    fun onResponderContainerClick() = _responderClickEvent.postValue(Event(Unit))

    fun onTryAgainClick() {
        val lastBaseCurrency = _latestRatesEntity.value?.baseCurrency ?: INITIAL_BASE_CURRENCY
        startRatesFlow(lastBaseCurrency)
    }

    fun onRatesItemClick(baseCurrency: String) {
        hideKeyboard()
        stopPreviousRatesFlow()
        startRatesFlow(baseCurrency)
    }

    private fun hideKeyboard() {
        _hideKeyboardEvent.value = (Event(Unit))
    }

    private fun stopPreviousRatesFlow() {
        if (disposables.size() > 0) disposables.clear()
    }

    private fun startRatesFlow(baseCurrency: String) {
        getRatesTask.invoke(baseCurrency)
            .doOnSubscribe { onNewRatesFlowRequest() }
            .subscribe(
                { latestRatesEntity -> onSuccess(latestRatesEntity) },
                { onError() }
            ).addTo(disposables)
    }

    private fun onNewRatesFlowRequest() = _loadingState.postValue(true)

    private fun onSuccess(latestRatesEntity: LatestRatesEntity) {
        if (isNewRateFlow(loadingState.value)) {
            _loadingState.postValue(false)
            _animationEvent.postValue(Event(Unit))
        }

        _latestRatesEntity.postValue(latestRatesEntity)
    }

    private fun isNewRateFlow(isLoading: Boolean?) = isLoading == true

    private fun onError() = _errorEvent.postValue(Event(Unit))

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    companion object {
        private const val INITIAL_BASE_CURRENCY = "EUR"
    }
}
