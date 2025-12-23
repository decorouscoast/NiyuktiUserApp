package com.example.niyukti.common.di

import android.content.Context
import com.example.niyukti.common.security.SecurityManager
import com.example.niyukti.common.storage.PlainSharedPreferencesManager
import com.example.niyukti.common.storage.SecureSharedPreferencesManager
import com.example.niyukti.common.storage.UnifiedStorageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing SecurityManager and Storage managers
 */
@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideSecurityManager(): SecurityManager {
        return SecurityManager()
    }
    
    @Provides
    @Singleton
    fun provideSecureSharedPreferencesManager(
        securityManager: SecurityManager,
        @ApplicationContext context: Context
    ): SecureSharedPreferencesManager {
        return SecureSharedPreferencesManager(securityManager, context)
    }
    
    @Provides
    @Singleton
    fun providePlainSharedPreferencesManager(
        @ApplicationContext context: Context
    ): PlainSharedPreferencesManager {
        return PlainSharedPreferencesManager(context)
    }
    
    @Provides
    @Singleton
    fun provideUnifiedStorageManager(
        securePrefs: SecureSharedPreferencesManager,
        plainPrefs: PlainSharedPreferencesManager
    ): UnifiedStorageManager {
        return UnifiedStorageManager(securePrefs, plainPrefs)
    }
}
