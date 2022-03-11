package com.paysera.currencyexchange.model.remote.http

import com.paysera.currencyexchange.model.remote.domain.RateStruct
import com.paysera.currencyexchange.model.remote.domain.ResponseStruct
import com.paysera.currencyexchange.phrase.Const
import retrofit2.http.*

interface RemoteApi {
    @GET(Const.LATEST_RATE_LIST_END_POINT)
    suspend fun fetchRateList(
        @Query("access_key") accessKey:String,
        @Query("format")format:Int,
    ): ResponseStruct<RateStruct>
}