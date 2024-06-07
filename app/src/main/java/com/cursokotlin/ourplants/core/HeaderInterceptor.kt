package com.cursokotlin.ourplants.core

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID P1Z74sAia_hNS7moZ9kyEqjrvp6dBvQHNsep2TIexWs")
            .build()
        return chain.proceed(request = request)
    }
}