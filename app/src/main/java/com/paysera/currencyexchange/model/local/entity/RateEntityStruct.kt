package com.paysera.currencyexchange.model.local.entity

import com.paysera.currencyexchange.model.remote.domain.RateStruct
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RateEntityStruct : RealmObject() {
    @PrimaryKey
    var unit: String = ""
    var value: Double = 0.0

    companion object {
        fun convert(reteStruct: RateStruct) =
            RateEntityStruct().apply {
                unit = reteStruct.unit
                value = reteStruct.value
            }
    }
}
