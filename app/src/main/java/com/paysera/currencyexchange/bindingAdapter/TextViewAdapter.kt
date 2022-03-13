package com.paysera.currencyexchange.bindingAdapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.paysera.currencyexchange.utils.TextEditor

@BindingAdapter("app:commaSeparator")
fun commaSeparator(textView: TextView, text: String) {
    textView.text = TextEditor.commaSeparator(text)
}
