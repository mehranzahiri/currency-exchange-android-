package com.paysera.currencyexchange.model.remote.domain

import com.paysera.currencyexchange.model.local.entity.RateEntityStruct

data class RateStruct(
    val unit: String,
    val value: Double,
){
    companion object{
        fun convert (rateEntityStruct: RateEntityStruct)=
            RateStruct(rateEntityStruct.unit,
                rateEntityStruct.value)

    }
}
