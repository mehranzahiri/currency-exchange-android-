package com.paysera.currencyexchange.viewModel

import com.hamekara.app.viewModel.BaseViewModel
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.notificationcenter.FireCenter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

abstract class ScopeViewModel : BaseViewModel() {
    protected val job = SupervisorJob(get<Job>())
    protected val scope =
        CoroutineScope(Main + job + CoroutineExceptionHandler { _, throwable ->
            FireCenter.coroutineException.postValue(throwable)
        })

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    data class ErrorData(
        val key: String,
        val message: String
    )
}