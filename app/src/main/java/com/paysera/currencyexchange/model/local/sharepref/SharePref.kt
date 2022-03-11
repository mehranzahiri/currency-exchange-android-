package com.paysera.currencyexchange.model.local.sharepref

import android.content.SharedPreferences

class SharePref {
    var mSharedPreferences: SharedPreferences = com.paysera.currencyexchange.di.get()


    fun remove(key: String?) {
        mSharedPreferences.edit().remove(key).apply()
    }

    fun put(key: String?, value: String?) {
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun put(key: String?, value: Int) {
        mSharedPreferences.edit().putInt(key, value).apply()
    }

    fun put(key: String?, value: Float) {
        mSharedPreferences.edit().putFloat(key, value).apply()
    }

    fun put(key: String?, value: Long) {
        mSharedPreferences.edit().putLong(key, value).apply()
    }

    fun put(key: String?, value: Boolean) {
        mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    operator fun get(key: String?, defaultValue: String?): String? {
        return mSharedPreferences.getString(key, defaultValue)
    }

    operator fun get(key: String?, defaultValue: Int): Int {
        return mSharedPreferences.getInt(key, defaultValue)
    }

    operator fun get(key: String?, defaultValue: Float): Float {
        return mSharedPreferences.getFloat(key, defaultValue)
    }

    operator fun get(key: String?, defaultValue: Boolean): Boolean {
        return mSharedPreferences.getBoolean(key, defaultValue)
    }

    operator fun get(key: String?, defaultValue: Long): Long {
        return mSharedPreferences.getLong(key, defaultValue)
    }

    fun deleteSavedData(key: String?) {
        mSharedPreferences.edit().remove(key).apply()
    }
}