package com.esperassignment.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.esperassignment.data.local.dao.DBDao
import com.esperassignment.data.local.entity.MExclusion
import com.esperassignment.data.local.entity.MFeature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalRepo(private val db: DBDao) {


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFeature(data: List<MFeature>) {
        withContext(Dispatchers.IO) {
            db.insertFeature(data)
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertExclusion(data: List<MExclusion>) {
        withContext(Dispatchers.IO) {
            db.insertExclusion(data)
        }
    }

    fun getFeatures(): LiveData<List<MFeature>> {
        return db.getFeatures()
    }

    fun getExclusions(): LiveData<List<MExclusion>> {
        return db.getExclusions()
    }
}