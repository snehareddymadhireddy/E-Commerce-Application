package com.example.finalproject1.remote

import com.example.finalproject1.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    val retrofit:Retrofit by lazy{
    val logInterceptor=HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
        val client = OkHttpClient.Builder().apply {
            addInterceptor(logInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
        }.build()
        Retrofit.Builder().apply {
            baseUrl(Utils.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
    }
    }
