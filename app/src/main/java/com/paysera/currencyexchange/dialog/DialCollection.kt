package com.paysera.currencyexchange.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.paysera.currencyexchange.R
import android.view.LayoutInflater
import android.view.View

import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.paysera.currencyexchange.databinding.DialogConvertBinding


object DialCollection {

    fun showConvertDialog(
        context: Context,
        from: String,
        to: String,
        fromUnit: String,
        toUnit: String,
        commission: String
    ) {
        val binding:DialogConvertBinding=DataBindingUtil.inflate(LayoutInflater.from(context),
            R.layout.dialog_convert,
            null,
            false)

        binding.from=from
        binding.fromUnit=fromUnit
        binding.to=to
        binding.toUnit=toUnit
        binding.commission=commission

        val dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(true)
            setContentView(binding.root)
        }

        binding.btnDone.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

}