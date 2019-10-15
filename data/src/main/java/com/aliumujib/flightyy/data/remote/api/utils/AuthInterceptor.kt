package com.aliumujib.flightyy.data.remote.api.utils

import android.util.Log
import com.aliumujib.flightyy.data.contracts.cache.IAuthTokenManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class AuthInterceptor @Inject constructor(private val authTokenManager: IAuthTokenManager?) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {

        val request = chain.request()

        val requestBuilder = request.newBuilder()

        authTokenManager?.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer ${authTokenManager.getToken()}")
        }

        var response: Response? = null

        try {
            response = chain.proceed(requestBuilder.build())
        } catch (e: Exception) {
            Log.d(TAG, "<-- HTTP FAILED: $e")
            throw e
        }

        return response
    }

    companion object {
        private val TAG = AuthInterceptor::class.java.canonicalName
    }
}