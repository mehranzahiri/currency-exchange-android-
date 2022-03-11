package com.paysera.currencyexchange.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Resources
import com.paysera.currencyexchange.setting.ContextConfig
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val contextModule = module {
    single { ContextConfig() }

    single {
    androidContext().contentResolver
    }

    single<Resources> {
        androidContext().resources
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            get<ContextConfig>().preferencesFileName, MODE_PRIVATE
        )
    }

    single<Job> { SupervisorJob() }
}

