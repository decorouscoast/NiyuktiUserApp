package com.example.niyukti.common.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Plain SharedPreferences manager for non-sensitive data
 * Direct storage without encryption for better performance
 */
@Singleton
class PlainSharedPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) : BaseSharedPreferencesManager() {
    
    override fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("plain_prefs", Context.MODE_PRIVATE)
    }
    
    /**
     * Store data without encryption
     */
    fun <T> putPlain(key: String, value: T): Boolean {
        return put(key, value, encrypt = false)
    }
    
    /**
     * Retrieve data without decryption
     */
    fun <T> getPlain(key: String, defaultValue: T): T {
        return get(key, defaultValue, encrypt = false)
    }
    
    /**
     * Clear all plain data
     */
    fun clearPlain(): Boolean {
        return clear()
    }
}
