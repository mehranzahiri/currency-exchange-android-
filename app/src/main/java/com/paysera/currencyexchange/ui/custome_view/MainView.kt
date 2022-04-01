package com.paysera.currencyexchange.ui.custome_view

import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.paysera.currencyexchange.R


class MainView(context: Context) : FrameLayout(context) {

    init {
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.controller_home, null)

        (view.findViewById(R.id.tv_title) as TextView).text = "Hello World"

        addView(view)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


    }
}