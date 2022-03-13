package com.paysera.currencyexchange.model.remote.domain

import com.paysera.currencyexchange.model.local.entity.WalletEntityStruct
import com.paysera.currencyexchange.ui.adapter.DiffItem

data class WalletStruct(
    val unit: String,
    val value: Double
):DiffItem {
    companion object {
        fun convert(walletEntityStruct: WalletEntityStruct) =
            WalletStruct(
                walletEntityStruct.unit,
                walletEntityStruct.value
            )

    }
}
