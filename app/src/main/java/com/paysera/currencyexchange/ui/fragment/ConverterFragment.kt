package com.paysera.currencyexchange.ui.fragment

import android.os.Bundle
import android.util.Log
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.databinding.FragmentConverterBinding
import com.paysera.currencyexchange.ui.template.MvvmFragment
import com.paysera.currencyexchange.viewModel.RateViewModel
import com.paysera.wheelpicker.WheelPicker

class ConverterFragment : MvvmFragment<FragmentConverterBinding, RateViewModel>(
    R.layout.fragment_converter,
    RateViewModel::class.java
) {

    override fun onEveryInitialization(savedBundle: Bundle?) {

        data.rateMediatorList.observe(viewLifecycleOwner){
            Log.e(javaClass.simpleName,javaClass.simpleName)
        }

        data.rateList.observe(viewLifecycleOwner){
            binding.l1.data=it.map {
                it.unit
            }

            binding.l2.data=it.map {
                it.unit
            }
            Log.i("ok",it.toString())
        }
        data.initRateListFragment()

        binding.l1.setOnItemSelectedListener { picker, item, position ->
            data.onSellWheelChange(position)
        }

        binding.l2.setOnItemSelectedListener { picker, item, position ->
            data.onBuyWheelChange(position)
        }

    }
}
