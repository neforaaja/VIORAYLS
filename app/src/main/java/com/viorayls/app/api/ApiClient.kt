package com.viorayls.app.api

import com.google.gson.Gson
import com.viorayls.app.util.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(AppConstants.CONNECT_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(AppConstants.READ_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private val gson = Gson()

    private val retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.ITUNES_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val lrclibRetrofit = Retrofit.Builder()
        .baseUrl(AppConstants.LRCLIB_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val itunesService: ItunesService by lazy { retrofit.create(ItunesService::class.java) }
    val lrclibService: LrclibService by lazy { lrclibRetrofit.create(LrclibService::class.java) }
}
