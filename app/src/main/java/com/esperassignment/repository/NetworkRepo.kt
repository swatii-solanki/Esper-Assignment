package com.esperassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.esperassignment.api.RetrofitClient
import com.esperassignment.model.MFeature
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepo {

    private val featureLiveData: MutableLiveData<List<MFeature>> = MutableLiveData()
    private var featureList: List<MFeature> = ArrayList()

    companion object {
        val networkRepo: NetworkRepo
            get() = NetworkRepo()
        private const val TAG = "NetworkRepo"
    }

    fun getFeatures(): MutableLiveData<List<MFeature>> {
        val call: Call<List<MFeature>> = RetrofitClient.RetrofitService().featureList()
        call.enqueue(object : Callback<List<MFeature>> {
            override fun onResponse(
                call: Call<List<MFeature>>,
                response: Response<List<MFeature>>
            ) {
                try {
                    if (response.isSuccessful) {
                        featureList = response.body()!!
                        featureLiveData.value = featureList
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "onResponse: ${e.message}")
                }
            }

            override fun onFailure(call: Call<List<MFeature>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
        return featureLiveData
    }

}