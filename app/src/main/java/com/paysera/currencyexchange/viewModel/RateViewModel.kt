package com.paysera.currencyexchange.viewModel

import android.os.CountDownTimer
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.repository.RateRepository
import com.paysera.currencyexchange.phrase.Const
import com.paysera.currencyexchange.utils.TextEditor
import kotlinx.coroutines.launch

class RateViewModel : ScopeViewModel() {
    private val rateRepository: RateRepository = get()

    val rateList = rateRepository.rateList
    val rateMediatorList = rateRepository.rateMediatorList

    val sellValue=MutableLiveData<String>().apply {
        value="enter some value for buy"
    }
    val buyValue=MutableLiveData<String>().apply {
        value="select some unit for sell"
    }
    private lateinit var timer: CountDownTimer

    var sellPosition=0
    var buyPosition=0

    val inputValue=ObservableField<String>()

    fun initRateListFragment() {
        fetchData()

        timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                fetchData()

                timer.start()
            }
        }
    }

    private fun fetchData() {
        showProgressDialog.value = true

        scope.launch {
            rateRepository.fetchRateList(
                accessKey = Const.ACCESS_TOKEN_KEY,
                format = 1
            )

            showProgressDialog.value = false
        }
    }


    fun onSellWheelChange(position:Int){
        sellPosition=position
    }

    fun onBuyWheelChange(position:Int){
        buyPosition=position
    }

    fun convertPrice(){
        if (inputValue.get()===null) {
            showMessage.value = R.string.rate_view_mode_invalid_input
            return
        }

        val from= rateList.value?.get(sellPosition)?.value
        if (from===null)return

        val to= rateList.value?.get(buyPosition)?.value
        if (to===null)return

        val diffPercent=to/from

        val inputAmount=inputValue.get()?.replace(",","")

        sellValue.value= "${inputAmount?.let { TextEditor.commaSeparator(it) }} ${rateList.value?.get(sellPosition)?.unit} ="
        buyValue.value= "${TextEditor.commaSeparator((inputAmount?.toDouble()!! * (diffPercent)).toInt().toString())} ${rateList.value?.get(buyPosition)?.unit}"
    }
}