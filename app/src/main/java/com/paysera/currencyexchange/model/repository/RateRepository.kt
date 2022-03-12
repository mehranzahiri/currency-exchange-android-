package com.paysera.currencyexchange.model.repository


import android.os.CountDownTimer
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.local.entity.RateEntityStruct
import com.paysera.currencyexchange.model.remote.domain.RateStruct
import com.paysera.currencyexchange.model.remote.http.RemoteApi
import com.paysera.currencyexchange.utils.LiveRealmResults
import io.realm.Realm


class RateRepository {

    private val remoteApi: RemoteApi = get()
    private val realm: Realm = get()
    private val rateEntityStruct: LiveRealmResults<RateEntityStruct> =
        LiveRealmResults(
            realm.where(
                RateEntityStruct::
                class.java
            ).findAllAsync()
        )

    var rateList = MutableLiveData<List<RateStruct>>()

    val rateMediatorList = MediatorLiveData<List<RateStruct>>().apply {
        addSource(rateEntityStruct) {
            rateList.postValue(it?.map { rateEntityStruct ->
                RateStruct.convert(rateEntityStruct)
            })
        }
    }

    suspend fun fetchRateList(accessKey: String, format: Int) {

        val result = remoteApi.fetchRateList(
            accessKey = accessKey,
            format = format
        )

        if (result.success == true)
            result.transferData<JsonObject>()?.let {
                saveRates(
                    it
                )
            }
    }

    private fun saveRates(rateListObj: JsonObject) {

        val tempList = ArrayList<RateStruct>()

        val entries: Set<Map.Entry<String, JsonElement>> =
            rateListObj.entrySet() //will return members of your object

        entries.forEachIndexed { index, entry ->
            tempList.add(RateStruct(entry.key, entry.value.toString().toDouble()))
        }


        realm.executeTransactionAsync {
            it.insertOrUpdate(tempList.map { rateStruct ->
                RateEntityStruct.convert(rateStruct)
            })
        }
    }

}