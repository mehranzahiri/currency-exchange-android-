package com.paysera.currencyexchange.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.paysera.currencyexchange.R
import com.paysera.currencyexchange.databinding.FragmentConverterBinding
import com.paysera.currencyexchange.dialog.DialCollection
import com.paysera.currencyexchange.ui.adapter.WalletAdapter
import com.paysera.currencyexchange.ui.template.MvvmFragment
import com.paysera.currencyexchange.utils.TextEditor
import com.paysera.currencyexchange.viewModel.RateViewModel

class ConverterFragment : MvvmFragment<FragmentConverterBinding, RateViewModel>(
    R.layout.fragment_converter,
    RateViewModel::class.java
) {
    private lateinit var adapter: WalletAdapter

    override fun onEveryInitialization(savedBundle: Bundle?) {
        adapter = WalletAdapter(data).apply {
            binding.recWallet.adapter = this
        }

        data.rateMediatorList.observe(viewLifecycleOwner){
            Log.e(javaClass.simpleName,javaClass.simpleName)
        }

        data.walletMediatorList.observe(viewLifecycleOwner){
            Log.e(javaClass.simpleName,javaClass.simpleName)
        }

        data.rateList.observe(viewLifecycleOwner){
            binding.pickkerWithdraw.data=it.map {
                it.unit
            }

            binding.pickerDeposit.data=it.map {
                it.unit
            }
        }

        data.convertDialog.observe(viewLifecycleOwner){
            it?.let {
                DialCollection.showConvertDialog(requireContext(),
                    it.getString("from",""),
                    it.getString("to",""),
                    it.getString("from_unit",""),
                    it.getString("to_unit",""),
                    it.getString("commission",""))
            }
        }

        data.walletList.observe(viewLifecycleOwner){
            adapter.updateCustom(it)
        }
        data.initRateListFragment()

        binding.pickkerWithdraw.setOnItemSelectedListener { picker, item, position ->
            data.onSellWheelChange(position)
        }

        binding.pickerDeposit.setOnItemSelectedListener { picker, item, position ->
            data.onBuyWheelChange(position)
        }

        binding.pickkerWithdraw.typeface= Typeface.createFromAsset(requireActivity().assets,"gilory.ttf")
        binding.pickerDeposit.typeface= Typeface.createFromAsset(requireActivity().assets,"gilory.ttf")

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

                data.onInputChange(p0)
            }
        })


    }
}
