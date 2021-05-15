package com.esperassignment.api

import com.esperassignment.model.MFeature
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET(API.FEATURES)
    fun featureList(): Call<List<MFeature>>
}