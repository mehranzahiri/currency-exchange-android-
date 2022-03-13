package com.paysera.currencyexchange.model.local.entity

import com.paysera.currencyexchange.model.remote.domain.WalletStruct
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class WalletEntityStruct : RealmObject() {
    @PrimaryKey
    var unit: String = ""
    var value: Double = 0.0

    companion object {
        fun convert(walletStruct: WalletStruct) =
            WalletEntityStruct().apply {
                unit = walletStruct.unit
                value = walletStruct.value
            }
    }
}
