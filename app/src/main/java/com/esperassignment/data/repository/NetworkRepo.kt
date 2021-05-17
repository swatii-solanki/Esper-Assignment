package com.esperassignment.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.esperassignment.data.api.RetrofitClient
import com.esperassignment.data.local.entity.MExclusion
import com.esperassignment.data.local.entity.MFeature
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepo {

    private val featureLiveData: MutableLiveData<List<MFeature>> = MutableLiveData()
    private val exclusionLiveData: MutableLiveData<List<List<MExclusion>>> = MutableLiveData()

    companion object {
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
                        featureLiveData.value = response.body()
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

    fun getExclusions(): MutableLiveData<List<List<MExclusion>>> {
        val call: Call<List<List<MExclusion>>> = RetrofitClient.RetrofitService().exclusionList()
        call.enqueue(object : Callback<List<List<MExclusion>>> {
            override fun onResponse(
                call: Call<List<List<MExclusion>>>,
                response: Response<List<List<MExclusion>>>
            ) {
                try {
                    if (response.isSuccessful) {
                        exclusionLiveData.value = response.body()
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "onResponse: ${e.message}")
                }
            }

            override fun onFailure(call: Call<List<List<MExclusion>>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })

        return exclusionLiveData
    }

}