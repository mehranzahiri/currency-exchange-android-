package com.paysera.currencyexchange.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

private const val NETWORK_MODULE = "network."

val HODHOD_OK_HTTP_QUALIFIER = named("${NETWORK_MODULE}hodhod_ok_http")
val HODHOD_RETROFIT_QUALIFIER = named("${NETWORK_MODULE}hodhod_retrofit")

val networkModule = module {
    single { NetworkConfig() }

//    single { TokenInterceptor() }

    single {
        val directory = File(androidContext().cacheDir, "okhttp")
        Cache(directory, get<CacheConfig>().networkCacheSizeBytes)
    }

    single<Gson> {

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


    single(HODHOD_OK_HTTP_QUALIFIER) {
        newOkHttpClient()
    }


    single(HODHOD_RETROFIT_QUALIFIER) {

        Retrofit.Builder()
            .client(get(HODHOD_OK_HTTP_QUALIFIER))
//            .baseUrl(BuildConfig.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

//    single {
//        get<Retrofit>(HODHOD_RETROFIT_QUALIFIER).create(RemoteApi::class.java)
//    }

}

private fun newOkHttpClient(): OkHttpClient {
    val config = get<NetworkConfig>()


    val dispatcher = Dispatcher().apply {
        maxRequests = config.maxRequests
        maxRequestsPerHost = config.maxRequestPerHost
    }

    return OkHttpClient().newBuilder()
        .dispatcher(dispatcher)
        .readTimeout(config.readTimeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(config.writeTimeoutSeconds, TimeUnit.SECONDS)
        .connectTimeout(config.connectTimeoutSeconds, TimeUnit.SECONDS)
//            .addInterceptor(get<TokenInterceptor>())
        .cache(get())
        .build()
}