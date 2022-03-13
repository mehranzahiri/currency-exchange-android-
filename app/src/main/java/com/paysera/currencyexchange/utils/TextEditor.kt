package com.paysera.currencyexchange.utils

import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object TextEditor {
    fun commaSeparator(p0 :String):String{
        return try {
            val originNumber = String.format("%.2f", p0.toDouble())
            val splitDecimal=originNumber.split(".")
            var originalString: String = splitDecimal[0]
            if (originalString.contains(",")) {
                originalString = originalString.replace(",".toRegex(), "")
            }
            val longval: Long = originalString.toLong()
            val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
            formatter.applyPattern("###,###,###,###,###,###,###,###,###,###")

            if (splitDecimal.size>1 &&splitDecimal[1].isNotBlank()){
               formatter.format(longval).plus(".").plus(splitDecimal[1]).replace(".00","")
            }else {
                formatter.format(longval)
            }


        } catch (nfe: NumberFormatException) {
            "0"
        }
    }
}