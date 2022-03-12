package com.paysera.currencyexchange.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.paysera.currencyexchange.BuildConfig
import com.paysera.currencyexchange.model.remote.http.RemoteApi
import com.paysera.currencyexchange.model.remote.http.TokenInterceptor
import com.paysera.currencyexchange.setting.CacheConfig
import com.paysera.currencyexchange.setting.NetworkConfig
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

private const val NETWORK_MODULE = "network."

val CURRENCY_OK_HTTP_QUALIFIER = named("${NETWORK_MODULE}currency_ok_http")
val CURRENCY_RETROFIT_QUALIFIER = named("${NETWORK_MODULE}currency_retrofit")

val networkModule = module {
    single { NetworkConfig() }

    single { TokenInterceptor() }

    single {
        val directory = File(androidContext().cacheDir, "okhttp")
        Cache(directory, get<CacheConfig>().networkCacheSizeBytes)
    }

    single<Gson> {

        //TODO : THIS BLOCK PROVIDE DATA FORMATTER FOR CONVERT RESPONSE
//        val runtimeTypeAdapterFactory: RuntimeTypeAdapterFactory<VodStruct> =
//                RuntimeTypeAdapterFactory
//                        .of(VodStruct::class.java, "type")
//                        .registerSubtype(MovieStructType::class.java, VodEnum.vod.name)
//                        .registerSubtype(CharacterStructType::class.java, VodEnum.persons.name)
        GsonBuilder()
            .setLenient()
//                .registerTypeAdapterFactory(runtimeTypeAdapterFactory)
            .create()
    }


    single(CURRENCY_OK_HTTP_QUALIFIER) {
        newOkHttpClient()
    }


    single(CURRENCY_RETROFIT_QUALIFIER) {

        Retrofit.Builder()
            .client(get(CURRENCY_OK_HTTP_QUALIFIER))
            .baseUrl(BuildConfig.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single {
        get<Retrofit>(CURRENCY_RETROFIT_QUALIFIER).create(RemoteApi::class.java)
    }

}

private fun newOkHttpClient(): OkHttpClient {
    val config: NetworkConfig = get()

    val dispatcher = Dispatcher().apply {
        maxRequests = config.maxRequests
        maxRequestsPerHost = config.maxRequestPerHost
    }

    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        val trustManagerFactory: TrustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)
        val trustManagers: Array<TrustManager> =
            trustManagerFactory.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            "Unexpected default trust managers:" + trustManagers.contentToString()
        }

        val trustManager =
            trustManagers[0] as X509TrustManager


        return OkHttpClient().newBuilder()
            .dispatcher(dispatcher)
            .readTimeout(config.readTimeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(config.writeTimeoutSeconds, TimeUnit.SECONDS)
            .connectTimeout(config.connectTimeoutSeconds, TimeUnit.SECONDS)
            .addInterceptor(get<TokenInterceptor>())
            .sslSocketFactory(sslSocketFactory, trustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}