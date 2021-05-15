package com.esperassignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esperassignment.model.MFeature
import com.esperassignment.repository.NetworkRepo

class MainActivityViewModel : ViewModel() {

    companion object {
        val networkRepo: NetworkRepo
            get() = NetworkRepo()
    }

    fun featureList(): LiveData<List<MFeature>> = networkRepo.getFeatures()
}