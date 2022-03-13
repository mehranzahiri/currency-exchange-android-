package com.paysera.currencyexchange.viewModel

import android.os.Handler
import android.os.Looper
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.remote.domain.WalletStruct
import com.paysera.currencyexchange.model.repository.AuthRepository
import com.paysera.currencyexchange.model.repository.WalletRepository

class AuthViewModel : ScopeViewModel() {
    private val authRepository: AuthRepository = get()
    private val walletRepository: WalletRepository = get()
    private val startDelay = 3000L

    fun initIntroFragment() {
        if (authRepository.isFirstInit()) {
            authRepository.setFirstInit()

            walletRepository.saveWallet(WalletStruct("EUR", 1000.0))
        }

        Handler(Looper.getMainLooper()).postDelayed({
            goTo.value=R.id.action_splashFragment_to_converterFragment
        }, startDelay)
    }
}