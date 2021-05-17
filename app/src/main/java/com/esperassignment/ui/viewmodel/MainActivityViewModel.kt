package com.esperassignment.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esperassignment.repository.LocalRepo
import com.esperassignment.local.entity.MExclusion
import com.esperassignment.local.entity.MFeature
import com.esperassignment.repository.NetworkRepo
import kotlinx.coroutines.launch

class MainActivityViewModel(private val localRepo: LocalRepo) : ViewModel() {

    companion object {
        val networkRepo: NetworkRepo
            get() = NetworkRepo()
    }

    fun getFeatureList(): LiveData<List<MFeature>> = networkRepo.getFeatures()

    fun getExclusionList(): LiveData<List<List<MExclusion>>> = networkRepo.getExclusions()

    fun insertFeature(feature: List<MFeature>) = viewModelScope.launch {
        localRepo.insertFeature(feature)
    }

    fun insertExclusion(exclusion: List<MExclusion>) = viewModelScope.launch {
        localRepo.insertExclusion(exclusion)
    }

    fun getFeatures() = localRepo.getFeatures()

    fun getExclusion() = localRepo.getExclusions()
}