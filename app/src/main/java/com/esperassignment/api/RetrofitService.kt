package com.esperassignment.api

import com.esperassignment.local.entity.MExclusion
import com.esperassignment.local.entity.MFeature
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    // Calling APIs

    @GET(API.FEATURES)
    fun featureList(): Call<List<MFeature>>

    @GET(API.EXCLUSIONS)
    fun exclusionList(): Call<List<List<MExclusion>>>
}