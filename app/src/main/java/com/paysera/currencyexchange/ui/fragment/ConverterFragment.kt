package com.paysera.currencyexchange.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.databinding.FragmentConverterBinding
import com.paysera.currencyexchange.ui.template.MvvmFragment
import com.paysera.currencyexchange.utils.TextEditor
import com.paysera.currencyexchange.viewModel.RateViewModel
import com.paysera.wheelpicker.WheelPicker
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

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

        binding.l1.typeface= Typeface.createFromAsset(requireActivity().assets,"gilory.ttf")
        binding.l2.typeface= Typeface.createFromAsset(requireActivity().assets,"gilory.ttf")

        binding.txtInput.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.txtInput.removeTextChangedListener(this)

                    val formattedString =TextEditor.commaSeparator(p0 = p0.toString())

                    //setting text after format to EditText
                    binding.txtInput.setText(formattedString)
                    binding.txtInput.text?.length?.let { binding.txtInput.setSelection(it) }

                binding.txtInput.addTextChangedListener(this)
            }
        })

    }
}
