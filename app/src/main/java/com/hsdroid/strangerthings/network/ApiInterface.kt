package com.hsdroid.strangerthings.network

import com.hsdroid.strangerthings.network.model.Quotes
import retrofit2.http.GET

interface ApiInterface {

    @GET("api/quotes/20")
    suspend fun getQuotes(): List<Quotes>

}