package com.hamekara.app.viewModel

import android.content.Intent
import android.os.Bundle
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.paysera.currencyexchange.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel() {
    private var recreationCount = 0

    val goTo = SingleLiveEvent<Int>()
    val goToDir = SingleLiveEvent<NavDirections>()
    val goToByBundle = SingleLiveEvent<Pair<Int,Bundle>>()
    val gotToActivity = SingleLiveEvent<Class<*>?>()
    val gotToActivityWithIntent = SingleLiveEvent<Intent>()

    val hideKeyboard = SingleLiveEvent<Unit>()
    val showMessage = SingleLiveEvent<Int>()
    val showMessageStr = SingleLiveEvent<String>()
    val socialTextIntent = SingleLiveEvent<String>()

    val showProgressDialog = SingleLiveEvent<Boolean>()

    var isFirstCreation = true
        private set

    @MainThread
    fun onCreate() {
        recreationCount++
        if (recreationCount > 1) isFirstCreation = false

    }


}