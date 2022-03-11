package com.paysera.currencyexchange.model.repository


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.local.entity.RateEntityStruct
import com.paysera.currencyexchange.model.remote.domain.RateStruct
import com.paysera.currencyexchange.model.remote.http.RemoteApi
import com.paysera.currencyexchange.utils.LiveRealmResults
import io.realm.Realm
import org.json.JSONException
import org.json.JSONObject

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

    suspend fun fetchRateListSlider(accessKey: String, format: Int) {

        val result = remoteApi.fetchRateList(
            accessKey = accessKey,
            format = format
        )

        if (result.success == true)
            result.transferData<JSONObject>()?.let {
                saveSlider(
                    it
                )
            }
    }

    private fun saveSlider(rateListObj: JSONObject) {
        val tempList = ArrayList<RateStruct>()

        val iter: Iterator<String> = rateListObj.keys()
        while (iter.hasNext()) {
            val key = iter.next()
            try {
                val value: Any = rateListObj.get(key)

                tempList.add(RateStruct(key, value as Double))
            } catch (e: JSONException) {
                // Something went wrong!
            }
        }

        realm.executeTransactionAsync {
            it.insertOrUpdate(tempList.map { rateStruct ->
                RateEntityStruct.convert(rateStruct)
            })
        }
    }

}