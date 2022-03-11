package com.paysera.currencyexchange

import android.app.Application
import com.paysera.currencyexchange.di.contextModule
import com.paysera.currencyexchange.di.networkModule
import com.paysera.currencyexchange.di.storageModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        instance = this

        startKoin {
            androidContext(this@App)
            modules(listOf(contextModule, networkModule, storageModule))
        }
    }

    fun exit() {
        android.os.Process.killProcess(android.os.Process.myPid())

    }
}