package com.paysera.currencyexchange.model.repository


import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.local.sharepref.SharePref

class AuthRepository {
    private val FIRST_INIT = "first_init"
    private val sharePref: SharePref = get()

    fun isFirstInit() = sharePref[FIRST_INIT, true]

    fun setFirstInit() {
        sharePref.put(FIRST_INIT, false)
    }

}