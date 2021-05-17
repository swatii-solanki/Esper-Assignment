package com.esperassignment.data.api

import com.esperassignment.data.local.entity.MExclusion
import com.esperassignment.data.local.entity.MFeature
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    // Calling APIs

    @GET(API.FEATURES)
    fun featureList(): Call<List<MFeature>>

    @GET(API.EXCLUSIONS)
    fun exclusionList(): Call<List<List<MExclusion>>>
}