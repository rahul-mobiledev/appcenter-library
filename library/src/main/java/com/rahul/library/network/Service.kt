package com.rahul.library.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("projects/release")
    fun getLatestVersion(@Query("id") apiKey: String): Call<UpdateResponse>
}