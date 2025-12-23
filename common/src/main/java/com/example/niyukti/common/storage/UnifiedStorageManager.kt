package com.example.niyukti.common.storage

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Unified storage manager that provides both secure and plain storage options
 * Automatically handles encryption/decryption and provides convenient methods
 */
@Singleton
class UnifiedStorageManager @Inject constructor(
    private val securePrefs: SecureSharedPreferencesManager,
    private val plainPrefs: PlainSharedPreferencesManager
) {

    /**
     * Store data with automatic encryption detection based on key patterns
     * Keys containing "token", "password", "secret", "key" will be encrypted
     */
    fun <T> put(key: String, value: T, encrypt: Boolean? = null): Boolean {
        val shouldEncrypt = encrypt ?: shouldEncryptKey(key)
        return if (shouldEncrypt) {
            securePrefs.putSecure(key, value)
        } else {
            plainPrefs.putPlain(key, value)
        }
    }

    /**
     * Retrieve data with automatic decryption detection
     */
    fun <T> get(key: String, defaultValue: T, encrypt: Boolean? = null): T {
        val shouldEncrypt = encrypt ?: shouldEncryptKey(key)
        return if (shouldEncrypt) {
            securePrefs.getSecure(key, defaultValue)
        } else {
            plainPrefs.getPlain(key, defaultValue)
        }
    }

    /**
     * Store data securely (encrypted)
     */
    fun <T> putSecure(key: String, value: T): Boolean {
        return securePrefs.putSecure(key, value)
    }

    /**
     * Retrieve data securely (decrypted)
     */
    fun <T> getSecure(key: String, defaultValue: T): T {
        return securePrefs.getSecure(key, defaultValue)
    }

    /**
     * Store data plainly (no encryption)
     */
    fun <T> putPlain(key: String, value: T): Boolean {
        return plainPrefs.putPlain(key, value)
    }

    /**
     * Retrieve data plainly (no decryption)
     */
    fun <T> getPlain(key: String, defaultValue: T): T {
        return plainPrefs.getPlain(key, defaultValue)
    }

    /**
     * Clear all data from both storage types
     */
    fun clearAll(): Boolean {
        val secureCleared = securePrefs.clearSecure()
        val plainCleared = plainPrefs.clearPlain()
        return secureCleared && plainCleared
    }

    /**
     * Clear only secure data
     */
    fun clearSecure(): Boolean {
        return securePrefs.clearSecure()
    }

    /**
     * Clear only plain data
     */
    fun clearPlain(): Boolean {
        return plainPrefs.clearPlain()
    }

    /**
     * Patterns for automatic encryption detection
     */
    companion object {
        private val SECURE_PATTERNS = setOf(
            "token", "password", "secret", "key", "auth", "credential",
            "session", "jwt", "oauth", "bearer", "api_key"
        )

        private val ENCRYPTION_KEYWORDS = setOf(
            "encrypted", "secure", "private", "confidential"
        )

        fun shouldEncryptKey(key: String): Boolean {
            val lowerKey = key.lowercase()
            return SECURE_PATTERNS.any { lowerKey.contains(it) } ||
                   ENCRYPTION_KEYWORDS.any { lowerKey.contains(it) }
        }
    }
}