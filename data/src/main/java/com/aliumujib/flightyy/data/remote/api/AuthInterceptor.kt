package com.aliumujib.flightyy.data.remote.api

import android.util.Log
import com.aliumujib.flightyy.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException



class AuthInterceptor(var apiKey: String) : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {

        val request = chain.request()

        val requestBuilder = request.newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer $apiKey")

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