package com.paysera.currencyexchange.viewModel

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.remote.domain.WalletStruct
import com.paysera.currencyexchange.model.repository.RateRepository
import com.paysera.currencyexchange.model.repository.WalletRepository
import com.paysera.currencyexchange.phrase.Const
import com.paysera.currencyexchange.utils.SingleLiveEvent
import com.paysera.currencyexchange.utils.TextEditor
import kotlinx.coroutines.launch

class RateViewModel : ScopeViewModel() {
    private val rateRepository: RateRepository = get()
    private val walletRepository: WalletRepository = get()

    val rateList = rateRepository.rateList
    val rateMediatorList = rateRepository.rateMediatorList

    val walletList = walletRepository.walletList
    val convertDialog = SingleLiveEvent<Bundle>()
    val walletMediatorList = walletRepository.walletMediatorList

    val sellValue = MutableLiveData<String>().apply {
        value = "enter some value for buy"
    }
    val buyValue = MutableLiveData<String>().apply {
        value = "select some unit for sell"
    }

    val showNetworkSnackbar = MutableLiveData<String>()
    val hideNetworkSnackbar = SingleLiveEvent<String>()

    var isNetWorkAvailable = true

    private lateinit var timer: CountDownTimer

    var sellPosition = 0
    var buyPosition = 0

    val inputValue = ObservableField<String>()

    fun initRateListFragment(networkConnected: Boolean) {
        isNetWorkAvailable = networkConnected

        // every minute we synchronize our data
        timer = object : CountDownTimer(60_000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if (isNetWorkAvailable) {
                    fetchData()

                    timer.start()
                }

            }
        }


        onNetWorkChanged(networkConnected)
    }

    // fetch list every 10 sec.
    private fun scheduleFetchRateList() {
        showProgressDialog.value = true
        fetchData()

        timer.start()
    }

    private fun fetchData() {

        scope.launch {
            rateRepository.fetchRateList(
                accessKey = Const.ACCESS_TOKEN_KEY,
                format = 1
            )

            showProgressDialog.value = false
        }
    }


    fun onSellWheelChange(position: Int) {
        sellPosition = position

        convertPrice()
    }

    fun onBuyWheelChange(position: Int) {
        buyPosition = position

        convertPrice()
    }

    fun onInputChange(amount: Editable?) {
        convertPrice()
    }

    private fun convertPrice() {
        if (inputValue.get().isNullOrBlank()) {
            showMessage.value = R.string.rate_view_mode_invalid_input
            return
        }

        val from = rateList.value?.get(sellPosition)?.value
        if (from === null) return

        val to = rateList.value?.get(buyPosition)?.value
        if (to === null) return

        val diffPercent = to / from

        val inputAmount = inputValue.get()?.replace(",", "")

        sellValue.value = "${inputAmount?.let { TextEditor.commaSeparator(it) }} ${
            rateList.value?.get(sellPosition)?.unit
        } ="
        buyValue.value = "${
            TextEditor.commaSeparator(
                (inputAmount?.toDouble()!! * (diffPercent)).toString()
            )
        } ${rateList.value?.get(buyPosition)?.unit}"
    }

    fun onSubmitClicked() {
        val from = rateList.value?.get(sellPosition)
        if (from === null) return

        val to = rateList.value?.get(buyPosition)
        if (to === null) return

        val inputAmount = inputValue.get()?.replace(",", "")

        if (inputAmount.isNullOrBlank()) {
            showMessage.value = R.string.rate_view_mode_invalid_input
            return
        }

        walletRepository.findWallet(from.unit)?.let { fromWallete ->
            if (fromWallete.value < inputAmount.toDouble()) {
                showMessageStr.value =
                    "Not enough ${from.unit} for exchange!\n First convert yout wallete to ${from.unit}."
                return@let
            }

            var decreaseAmount = fromWallete.value - inputAmount.toDouble()

            var commission = 0.0
            if (walletRepository.getExchangeCounter() > 5) {
                commission =
                    Math.round((inputAmount.toDouble() * Const.COMMISSION_AMOUNT) * 100.0) / 100.0
            }

            decreaseAmount -= commission

            walletRepository.saveWallet(WalletStruct(from.unit, decreaseAmount))


            val toWallet = walletRepository.findWallet(to.unit)

            val diffPercent = to.value / from.value


            val increaseAmount = (inputAmount.toDouble() * (diffPercent))
            var finalWalletAmount = 0.0
            toWallet?.let { toWallet ->
                finalWalletAmount = (toWallet.value + increaseAmount)
            } ?: kotlin.run {
                finalWalletAmount = increaseAmount
            }

            walletRepository.saveWallet(WalletStruct(to.unit, finalWalletAmount))

            convertDialog.postValue(Bundle().apply {
                putString("from", TextEditor.commaSeparator(inputAmount))
                putString("to", TextEditor.commaSeparator(increaseAmount.toString()))
                putString("from_unit", fromWallete.unit)
                putString("to_unit", toWallet?.unit)
                putString("commission", "-${TextEditor.commaSeparator(commission.toString())} ")
            })

            walletRepository.increaseExchangeCounter()
        } ?: kotlin.run {
            showMessageStr.value =
                "Not enough ${from.unit} for exchange!\n First convert your wallet to ${from.unit}."
        }

    }

    // observe network state
    fun onNetWorkChanged(status: Boolean?) {
        status?.let {
            isNetWorkAvailable = it

            if (status == true) {
                hideNetworkSnackbar.call()

                // restart schedule fetch data
                scheduleFetchRateList()
            } else {
                showNetworkSnackbar.postValue("Mobile Network Not Available!")
            }
        }

    }
}