package com.tranphuc.data.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.tranphuc.data.remote.api.PersonService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://randomuser.me/"


val networkModule = module {
    single { createWebService<PersonService>(BASE_URL) }
}


inline fun <reified T> createWebService(baseUrl: String): T {


    val okhttpClientBuilder = OkHttpClient.Builder()
    okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
    okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
    okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)


    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .client(okhttpClientBuilder.build())
        .build()
    return retrofit.create(T::class.java)
}