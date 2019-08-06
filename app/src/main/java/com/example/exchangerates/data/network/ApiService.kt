package com.example.exchangerates.data.network

import com.example.exchangerates.data.model.RootObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    fun getRates(@Query("base") base: String): Single<RootObject>
}