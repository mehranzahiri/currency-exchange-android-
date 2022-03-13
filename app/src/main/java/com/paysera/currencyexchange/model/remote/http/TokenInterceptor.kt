package com.paysera.currencyexchange.model.remote.http


import okhttp3.Interceptor
import okhttp3.Response

//TODO WE SHOULD SET ACCESS TOKEN ON HEADER!
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val requestBody = request()
            .newBuilder()
            .build()

        return proceed(requestBody)

    }
}