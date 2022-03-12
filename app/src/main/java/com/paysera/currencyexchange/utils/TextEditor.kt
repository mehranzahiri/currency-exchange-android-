package com.paysera.currencyexchange.utils

import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object TextEditor {
    fun commaSeparator(p0 :String):String{
        return try {
            var originalString: String = p0
            if (originalString.contains(",")) {
                originalString = originalString.replace(",".toRegex(), "")
            }
            val longval: Long = originalString.toLong()
            val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
            formatter.applyPattern("###,###,###,###,###,###,###,###,###,###")
            formatter.format(longval)

        } catch (nfe: NumberFormatException) {
            ""
        }
    }
}