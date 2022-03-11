package com.paysera.currencyexchange.model.remote.http


import com.paysera.currencyexchange.phrase.Const
import okhttp3.Interceptor
import okhttp3.Response
import com.paysera.currencyexchange.model.local.sharepref.SharePref
import com.paysera.currencyexchange.di.get

//TODO WE SHOULD SET ACCESS TOKEN ON HEADER!
class TokenInterceptor : Interceptor {
    private val sharePref: SharePref = get()

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharePref[Const.ACCESS_TOKEN_KEY, null]?.let {
            val requestBody = request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()

            return proceed(requestBody)
        }

        return proceed(request())

    }
}