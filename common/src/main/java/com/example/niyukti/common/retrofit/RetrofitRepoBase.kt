package com.example.niyukti.common.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Base repository class that provides Retrofit instance with mandatory headers
 * All module repositories should extend this class for consistent API handling
 */
open class RetrofitRepoBase @Inject constructor(
    private val retrofit: Retrofit,
    private val okHttpClient: OkHttpClient
) {
    
    protected val retrofitInstance: Retrofit = retrofit
    
    /**
     * Creates an API service implementation for the specified class
     * @param serviceClass The API service interface class
     * @return The implemented API service
     */
    protected fun <T> createApiService(serviceClass: Class<T>): T {
        return retrofitInstance.create(serviceClass)
    }
    
    /**
     * Creates a Retrofit instance with custom headers for this repository
     * @param baseUrl The base URL for this repository
     * @param headers Map of headers to include
     * @return Configured Retrofit instance
     */
    protected fun createRetrofitWithHeaders(
        baseUrl: String,
        headers: Map<String, String> = emptyMap()
    ): Retrofit {
        val headerInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
            
            // Add mandatory headers
            requestBuilder.addHeader("Content-Type", "application/json")
            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("User-Agent", "NiyuktiApp/1.0")
            
            // Add custom headers
            headers.forEach { (key, value) ->
                requestBuilder.addHeader(key, value)
            }
            
            chain.proceed(requestBuilder.build())
        }
        
        val clientWithHeaders = okHttpClient.newBuilder()
            .addInterceptor(headerInterceptor)
            .build()
        
        return retrofit.newBuilder()
            .baseUrl(baseUrl)
            .client(clientWithHeaders)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    /**
     * Creates an API service with custom headers
     * @param serviceClass The API service interface class
     * @param baseUrl The base URL for this service
     * @param headers Map of headers to include
     * @return The implemented API service with custom headers
     */
    protected fun <T> createApiServiceWithHeaders(
        serviceClass: Class<T>,
        baseUrl: String,
        headers: Map<String, String> = emptyMap()
    ): T {
        return createRetrofitWithHeaders(baseUrl, headers).create(serviceClass)
    }
    
    /**
     * Gets the base URL from the current Retrofit instance
     * @return The base URL string
     */
    protected fun getBaseUrl(): String {
        return retrofitInstance.baseUrl().toString()
    }
    
    /**
     * Common headers that should be included in all requests
     */
    companion object {
        const val HEADER_CONTENT_TYPE = "Content-Type"
        const val HEADER_ACCEPT = "Accept"
        const val HEADER_USER_AGENT = "User-Agent"
        const val HEADER_AUTHORIZATION = "Authorization"
        
        const val VALUE_APPLICATION_JSON = "application/json"
        const val VALUE_USER_AGENT = "NiyuktiApp/1.0"
    }
}
