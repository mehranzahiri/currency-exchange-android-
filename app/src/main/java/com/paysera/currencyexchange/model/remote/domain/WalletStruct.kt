package com.paysera.currencyexchange.model.remote.domain

import com.paysera.currencyexchange.model.local.entity.WalletEntityStruct

data class WalletStruct(
    val unit: String,
    val value: Double
) {
    companion object {
        fun convert(walletEntityStruct: WalletEntityStruct) =
            WalletStruct(
                walletEntityStruct.unit,
                walletEntityStruct.value
            )

    }
}
