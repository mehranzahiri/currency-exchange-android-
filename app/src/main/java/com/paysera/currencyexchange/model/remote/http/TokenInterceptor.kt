package com.paysera.currencyexchange.model.remote.http


import com.hamekara.app.phrase.Const
import okhttp3.Interceptor
import okhttp3.Response
import com.hamekara.app.model.local.sharepref.SharePref
import com.hodhod.tv.di.get

class TokenInterceptor : Interceptor {
    private val sharePref: SharePref = get()

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharePref[Const.TOKEN_KEY, null]?.let {
            val requestBody = request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()

            return proceed(requestBody)
        }

        return proceed(request())

    }
}