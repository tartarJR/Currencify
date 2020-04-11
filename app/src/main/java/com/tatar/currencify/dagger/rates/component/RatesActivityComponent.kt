package com.tatar.currencify.dagger.rates.component

import com.tatar.currencify.dagger.app.component.AppComponent
import com.tatar.currencify.dagger.rates.scope.ActivityScope
import com.tatar.currencify.ui.RatesActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface RatesActivityComponent {
    fun inject(ratesActivity: RatesActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): RatesActivityComponent
    }
}
