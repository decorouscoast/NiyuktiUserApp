package com.example.niyukti.common.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Base abstract class for SharedPreferences operations with generic type support
 * Provides serialization/deserialization for any data type
 */
abstract class BaseSharedPreferencesManager {
    
    protected abstract fun getSharedPreferences(): SharedPreferences
    
    protected val gson = Gson()
    
    /**
     * Store any data type with optional encryption
     */
    protected fun <T> put(key: String, value: T, encrypt: Boolean = false): Boolean {
        return try {
            val serializedValue = serialize(value)
            val finalValue = if (encrypt) {
                // Will be encrypted in extending classes
                serializedValue
            } else {
                serializedValue
            }
            
            getSharedPreferences().edit()
                .putString(key, finalValue)
                .apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * Retrieve any data type with optional decryption
     */
    protected fun <T> get(key: String, defaultValue: T, encrypt: Boolean = false): T {
        return try {
            val storedValue = getSharedPreferences().getString(key, null) ?: return defaultValue

            val finalValue = if (encrypt) {
                // Will be decrypted in extending classes
                storedValue
            } else {
                storedValue
            }
            
            deserialize(finalValue, defaultValue!!::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
    
    /**
     * Remove a key from SharedPreferences
     */
    protected fun remove(key: String): Boolean {
        return try {
            getSharedPreferences().edit().remove(key).commit()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * Check if a key exists in SharedPreferences
     */
    protected fun contains(key: String): Boolean {
        return getSharedPreferences().contains(key)
    }
    
    /**
     * Clear all data from SharedPreferences
     */
    protected fun clear(): Boolean {
        return try {
            getSharedPreferences().edit().clear().commit()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Serialize any object to JSON string
     */
    protected fun <T> serialize(value: T): String {
        return gson.toJson(value)
    }

    /**
     * Deserialize JSON string to any object
     */
    protected fun <T> deserialize(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    /**
     * Deserialize JSON string to any object with TypeToken
     */
    protected fun <T> deserialize(json: String, typeToken: TypeToken<T>): T {
        return gson.fromJson(json, typeToken)
    }
}
