package com.example.niyukti.common.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.nio.ByteBuffer
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Simple security manager for AES 256 GCM encryption using Android KeyStore
 */
@Singleton
class SecurityManager @Inject constructor() {
    
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
    private val keyAlias = "niyukti_encryption_key"
    
    init {
        generateKey()
    }
    
    /**
     * Encrypts a string using AES 256 GCM
     * @param data The string to encrypt
     * @return Base64 encoded encrypted string, or null if encryption fails
     */
    fun encrypt(data: String): String? {
        return try {
            val key = getKey() ?: return null
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            
            // Generate random IV
            val iv = ByteArray(12)
            java.security.SecureRandom().nextBytes(iv)
            
            cipher.init(Cipher.ENCRYPT_MODE, key, javax.crypto.spec.GCMParameterSpec(128, iv))
            val ciphertext = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            
            // Combine IV and ciphertext
            val combined = ByteBuffer.allocate(4 + iv.size + ciphertext.size)
                .putInt(iv.size)
                .put(iv)
                .put(ciphertext)
                .array()
            
            android.util.Base64.encodeToString(combined, android.util.Base64.NO_WRAP)
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Decrypts a Base64 encoded string using AES 256 GCM
     * @param encryptedData The Base64 encoded encrypted string
     * @return The decrypted string, or null if decryption fails
     */
    fun decrypt(encryptedData: String): String? {
        return try {
            val key = getKey() ?: return null
            val combined = android.util.Base64.decode(encryptedData, android.util.Base64.NO_WRAP)
            val buffer = ByteBuffer.wrap(combined)
            
            // Extract IV
            val ivSize = buffer.int
            val iv = ByteArray(ivSize)
            buffer.get(iv)
            
            // Extract ciphertext
            val ciphertext = ByteArray(buffer.remaining())
            buffer.get(ciphertext)
            
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, key, javax.crypto.spec.GCMParameterSpec(128, iv))
            val decryptedBytes = cipher.doFinal(ciphertext)
            
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            null
        }
    }
    
    private fun generateKey() {
        if (!keyStore.containsAlias(keyAlias)) {
            try {
                val keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore"
                )
                
                val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                    keyAlias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setKeySize(256)
                    .setRandomizedEncryptionRequired(true)
                    .build()
                
                keyGenerator.init(keyGenParameterSpec)
                keyGenerator.generateKey()
            } catch (e: Exception) {
                // Key generation failed
            }
        }
    }
    
    private fun getKey(): java.security.Key? {
        return try {
            val entry = keyStore.getEntry(keyAlias, null) as? KeyStore.SecretKeyEntry
            entry?.secretKey
        } catch (e: Exception) {
            null
        }
    }
}
