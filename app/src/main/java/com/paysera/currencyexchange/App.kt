package com.paysera.currencyexchange

import android.app.Application
import android.content.Context
import android.os.Handler
import com.paysera.currencyexchange.di.contextModule
import com.paysera.currencyexchange.di.networkModule
import com.paysera.currencyexchange.di.storageModule
import com.paysera.currencyexchange.utils.NetworkLiveData
import io.realm.Realm
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        @JvmStatic
        internal lateinit var applicationHandler: Handler
        internal var sSelf: App? = null

        val TAG: String = App::class.java.simpleName

        @JvmStatic
        fun get(): App = sSelf ?: App()

        fun from(context: Context) = context.applicationContext as App
    }

    init {
        sSelf = this
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        startKoin {
            androidContext(this@App)
            modules(listOf(contextModule, networkModule, storageModule))
        }
    }

    fun exit() {
        android.os.Process.killProcess(android.os.Process.myPid())

    }


}