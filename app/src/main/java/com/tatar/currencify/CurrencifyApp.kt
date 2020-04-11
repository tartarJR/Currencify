package com.tatar.currencify

import android.app.Application
import android.content.Context
import com.tatar.currencify.dagger.app.component.AppComponent
import com.tatar.currencify.dagger.app.component.DaggerAppComponent

class CurrencifyApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        createDaggerAppComponent()
    }

    private fun createDaggerAppComponent() {
        appComponent = DaggerAppComponent
            .factory()
            .create(this.applicationContext)
    }

    companion object {
        @JvmStatic
        fun appComponent(context: Context) =
            (context.applicationContext as CurrencifyApp).appComponent
    }
}
