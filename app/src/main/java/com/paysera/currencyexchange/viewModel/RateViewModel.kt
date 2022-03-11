package com.paysera.currencyexchange.viewModel

import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.repository.RateRepository
import com.paysera.currencyexchange.phrase.Const
import kotlinx.coroutines.launch

class RateViewModel : ScopeViewModel() {
    private val rateRepository: RateRepository = get()

    val categoryProductParentList = rateRepository.rateList
    val cardShopMediatorList = rateRepository.rateMediatorList

    fun initRateListFragment(marketId: Int, categoryId: Int) {
        showProgressDialog.value = true

        scope.launch {
            rateRepository.fetchRateListSlider(
                accessKey = Const.ACCESS_TOKEN_KEY,
                format = 1
            )

            showProgressDialog.value = false
        }
    }

}