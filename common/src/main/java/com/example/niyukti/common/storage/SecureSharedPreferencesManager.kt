package com.example.niyukti.common.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.niyukti.common.security.SecurityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Secure SharedPreferences manager that encrypts data before storing
 * Uses SecurityManager for AES 256 GCM encryption
 */
@Singleton
class SecureSharedPreferencesManager @Inject constructor(
    private val securityManager: SecurityManager,
    @ApplicationContext private val context: Context
) : BaseSharedPreferencesManager() {
    
    override fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
    }
    
    /**
     * Store data with encryption
     */
    fun <T> putSecure(key: String, value: T): Boolean {
        return try {
            val serializedValue = serialize(value)
            val encryptedValue = securityManager.encrypt(serializedValue)
            encryptedValue?.let { encrypted ->
                getSharedPreferences().edit {
                    putString(key, encrypted)
                }
                true
            } ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * Retrieve and decrypt data
     */
    fun <T> getSecure(key: String, defaultValue: T): T {
        return try {
            val encryptedValue = getSharedPreferences().getString(key, null) ?: return defaultValue

            val decryptedValue = securityManager.decrypt(encryptedValue)
            decryptedValue?.let { decrypted ->
                deserialize(decrypted, defaultValue!!::class.java)
            } ?: defaultValue
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }
    
    /**
     * Clear all secure data
     */
    fun clearSecure(): Boolean {
        return clear()
    }
}
