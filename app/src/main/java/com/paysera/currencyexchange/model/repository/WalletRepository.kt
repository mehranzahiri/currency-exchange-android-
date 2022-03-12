package com.paysera.currencyexchange.model.repository


import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.paysera.currencyexchange.di.get
import com.paysera.currencyexchange.model.local.entity.WalletEntityStruct
import com.paysera.currencyexchange.model.remote.domain.WalletStruct
import com.paysera.currencyexchange.utils.LiveRealmResults
import io.realm.Realm


class WalletRepository {

    private val realm: Realm = get()
    private val walletEntityStruct: LiveRealmResults<WalletEntityStruct> =
        LiveRealmResults(
            realm.where(
                WalletEntityStruct::
                class.java
            ).findAllAsync()
        )

    var walletList = MutableLiveData<List<WalletStruct>>()

    val rateMediatorList = MediatorLiveData<List<WalletStruct>>().apply {
        addSource(walletEntityStruct) {
            walletList.postValue(it?.map { walletEntityStruct ->
                WalletStruct.convert(walletEntityStruct)
            })
        }
    }


    private fun saveWallet(walletStruct: WalletStruct) {
        realm.executeTransactionAsync {
            it.insertOrUpdate(
                WalletEntityStruct.convert(walletStruct)
            )
        }
    }

}