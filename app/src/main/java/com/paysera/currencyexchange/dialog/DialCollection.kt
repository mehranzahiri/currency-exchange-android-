package com.paysera.currencyexchange.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Window
import com.paysera.currencyexchange.R
import android.view.LayoutInflater
import android.view.View

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.paysera.currencyexchange.databinding.DialogConvertBinding
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.provider.Settings


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


    fun showNetworkErrorSnackbar(view:View,message:String):Snackbar{
        val snackbar = Snackbar.make(view, message,
            Snackbar.LENGTH_INDEFINITE).setAction("Turn On") {
            Intent(Settings.ACTION_WIFI_SETTINGS).also { intent ->
                view.context.startActivity(intent)
            }
        }
        snackbar.setActionTextColor(Color.WHITE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(ContextCompat.getColor(view.context,R.color.original_orange))
        val textView =
            snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 16f
        snackbar.show()

        return snackbar
    }
}