package com.paysera.currencyexchange.model.remote.domain

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.paysera.currencyexchange.di.get
import retrofit2.HttpException
import java.io.IOException

data class ResponseStruct<T>(
    @Expose
    var code: Int,

    @SerializedName("success") @Expose
    var success: Boolean? = null,

    @SerializedName("title") @Expose
    var title: String? = null,

    @SerializedName("message") @Expose
    var message: String? = null,
    @SerializedName("rates")
    val rates: T?
) {

    @SuppressWarnings("unchecked")
    fun <U> transferData(): U? {
        return rates as U
    }

    companion object {
        const val SUCCESS_FULL = 200
        const val NOT_FOUND = 404
        const val UN_AUTHORIZED = 401
        const val FORBIDDEN = 403
        const val SERVER_ERROR = 500
        const val GENERAL_ERROR = -1
        const val BAD_REQUEST = 400
        const val TOO_MANY_REQUEST = 429

        private val gson: Gson = get()

        fun parseError(throwable: Throwable): ResponseStruct<*> {
            return when (throwable) {
                is IOException -> ResponseStruct(
                    code =-1,
                    title = throwable.message,
                    message = throwable.localizedMessage,
                    rates = null
                )

                is HttpException -> {
                    convertErrorBody(throwable)
                }

                else -> {
                    ResponseStruct(code =-1,
                        title = throwable.message,
                        message = throwable.localizedMessage,
                        rates = null)
                }
            }
        }

        private fun convertErrorBody(throwable: HttpException): ResponseStruct<*> {
            return try {
                throwable.response()?.errorBody()?.string()?.let {

                    gson.fromJson(it, ResponseStruct::class.java).apply {
                        code = throwable.code()
                    }
                } ?: kotlin.run {
                    ResponseStruct(
                        code = throwable.code(),
                        rates = null,
                        message = throwable.message()
                    )
                }
            } catch (exception: Exception) {
                ResponseStruct(code = -1,
                    title = exception.message,
                    rates = null,
                    message = exception.localizedMessage)
            }
        }
    }

}