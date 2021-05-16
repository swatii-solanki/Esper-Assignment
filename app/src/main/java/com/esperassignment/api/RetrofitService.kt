package com.esperassignment.api

import com.esperassignment.model.MDB
import com.esperassignment.model.MFeature
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

//    @GET(API.FEATURES)
//    fun featureList(): Call<List<MFeature>>

    @GET(API.DB)
    fun dbList(): Call<MDB>
}