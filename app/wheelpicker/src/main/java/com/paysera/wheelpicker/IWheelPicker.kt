package com.paysera.wheelpicker

import android.graphics.Typeface
import com.paysera.wheelpicker.WheelPicker.OnWheelChangeListener

interface IWheelPicker {
    var visibleItemCount: Int
    var isCyclic: Boolean
    fun setOnItemSelectedListener(listener: WheelPicker.OnItemSelectedListener?)
    var selectedItemPosition: Int
    val currentItemPosition: Int
    var data: List<*>?
    fun setSameWidth(hasSameSize: Boolean)
    fun hasSameWidth(): Boolean
    fun setOnWheelChangeListener(listener: OnWheelChangeListener?)
    var maximumWidthText: String?
    var maximumWidthTextPosition: Int
    var selectedItemTextColor: Int
    var itemTextColor: Int
    var itemTextSize: Int
    var itemSpace: Int
    fun setIndicator(hasIndicator: Boolean)
    fun hasIndicator(): Boolean
    var indicatorSize: Int
    var indicatorColor: Int
    fun setCurtain(hasCurtain: Boolean)
    fun hasCurtain(): Boolean
    var curtainColor: Int
    fun setAtmospheric(hasAtmospheric: Boolean)
    fun hasAtmospheric(): Boolean
    var isCurved: Boolean
    var itemAlign: Int
    var typeface: Typeface?
}