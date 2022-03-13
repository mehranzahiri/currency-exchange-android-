package com.paysera.currencyexchange.model.repository


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.local.entity.WalletEntityStruct
import com.paysera.currencyexchange.model.local.sharepref.SharePref
import com.paysera.currencyexchange.model.remote.domain.WalletStruct
import com.paysera.currencyexchange.utils.LiveRealmResults
import io.realm.Realm


class WalletRepository {
    private val EXCHANGE_COUNTER = "exchange_counter"

    private val realm: Realm = get()
    private val sharePref: SharePref = get()

    private val walletEntityStruct: LiveRealmResults<WalletEntityStruct> =
        LiveRealmResults(
            realm.where(
                WalletEntityStruct::
                class.java
            ).findAllAsync()
        )

    var walletList = MutableLiveData<List<WalletStruct>>()

    val walletMediatorList = MediatorLiveData<List<WalletStruct>>().apply {
        addSource(walletEntityStruct) {
            walletList.postValue(it?.map { walletEntityStruct ->
                WalletStruct.convert(walletEntityStruct)
            })
        }
    }


    fun saveWallet(walletStruct: WalletStruct) {
        realm.executeTransactionAsync {
            it.insertOrUpdate(
                WalletEntityStruct.convert(walletStruct)
            )
        }
    }

    fun findWallet(unit: String): WalletStruct? = realm.where(
        WalletEntityStruct::class.java
    ).equalTo("unit", unit).findFirst()?.let {
        WalletStruct.convert(
            it
        )
    }

    fun increaseExchangeCounter() {
        sharePref.put(EXCHANGE_COUNTER, sharePref[EXCHANGE_COUNTER, 0] + 1)
    }

    fun getExchangeCounter():Int =
        sharePref[EXCHANGE_COUNTER, 0]



}