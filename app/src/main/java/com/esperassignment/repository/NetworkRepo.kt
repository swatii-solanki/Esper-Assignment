package com.esperassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.esperassignment.api.RetrofitClient
import com.esperassignment.model.MDB
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkRepo {

    private val featureLiveData: MutableLiveData<MDB> = MutableLiveData()

    companion object {
        private const val TAG = "NetworkRepo"
    }

    fun getDB(): MutableLiveData<MDB> {
        val call: Call<MDB> = RetrofitClient.RetrofitService().dbList()
        call.enqueue(object : Callback<MDB> {
            override fun onResponse(
                call: Call<MDB>,
                response: Response<MDB>
            ) {
                try {
                    if (response.isSuccessful) {
                        featureLiveData.value = response.body()
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "onResponse: ${e.message}")
                }
            }

            override fun onFailure(call: Call<MDB>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })

        return featureLiveData
    }

}