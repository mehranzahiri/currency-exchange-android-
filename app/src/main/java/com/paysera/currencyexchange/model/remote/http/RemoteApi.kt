package com.paysera.currencyexchange.model.remote.http

import com.paysera.currencyexchange.model.remote.domain.*
import com.paysera.currencyexchange.phrase.Const.LATEST_CHANGELOG_END_POINT
import retrofit2.http.*


interface RemoteApi {

    @GET(LATEST_CHANGELOG_END_POINT)
    suspend fun orderList(
    ): ResponseStruct<List<OrderStruct>>


}