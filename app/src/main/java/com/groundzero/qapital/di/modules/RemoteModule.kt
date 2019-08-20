package com.groundzero.qapital.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.groundzero.qapital.data.goal.GoalApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RemoteModule {

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideHttpClient())
            .build()

    @Provides
    fun provideGoalApi(): GoalApi = provideRetrofit().create(GoalApi::class.java)

    companion object {
        const val BASE_URL = "http://qapital-ios-testtask.herokuapp.com"
    }
}