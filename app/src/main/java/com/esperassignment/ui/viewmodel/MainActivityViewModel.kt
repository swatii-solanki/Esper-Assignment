package com.esperassignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esperassignment.model.MFeature
import com.esperassignment.repository.NetworkRepo

class MainActivityViewModel : ViewModel() {

    private val networkRepo: NetworkRepo = NetworkRepo.networkRepo

    fun featureList(): LiveData<List<MFeature>> = networkRepo.getFeatures()
}