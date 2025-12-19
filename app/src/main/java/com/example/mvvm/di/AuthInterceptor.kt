package com.example.mvvm.di

import com.example.mvvm.data.retrofit.RequiresAuth
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor(
    val authToken: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requiresAuth =
            request.tag(Invocation::class.java)
                ?.method()
                ?.getAnnotation(RequiresAuth::class.java) != null

        if (!requiresAuth) {
            return chain.proceed(request)
        }

        val authedRequest =
            request.newBuilder()
                .addHeader("Authorization", "Bearer $authToken")
                .build()

        return chain.proceed(authedRequest)
    }
}
