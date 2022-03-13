package com.paysera.currencyexchange.ui.adapter

interface DiffItem {
    fun id(): Any? = hashCode()
}