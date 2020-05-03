package com.tatar.currencify.dagger.currency.component

import com.tatar.currencify.dagger.app.component.AppComponent
import com.tatar.currencify.dagger.currency.scope.ActivityScope
import com.tatar.currencify.ui.feature.CurrencyConversionActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface CurrencyConversionComponent {
    fun inject(currencyConversionActivity: CurrencyConversionActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): CurrencyConversionComponent
    }
}
