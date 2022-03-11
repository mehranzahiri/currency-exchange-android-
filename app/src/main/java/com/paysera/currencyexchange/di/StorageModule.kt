package com.paysera.currencyexchange.di

import com.paysera.currencyexchange.setting.CacheConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.dsl.module

val storageModule = module {
    single { CacheConfig() }

    single {
        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .schemaVersion(2)
            .build()

        Realm.setDefaultConfiguration(config)

        Realm.getDefaultInstance()
    }
}