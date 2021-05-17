package com.esperassignment.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esperassignment.local.entity.MExclusion
import com.esperassignment.local.entity.MFeature

@Dao
interface DBDao {

    @Query("SELECT * from features")
    fun getFeatures(): LiveData<List<MFeature>>

    @Query("SELECT * from exclusions")
    fun getExclusions(): LiveData<List<MExclusion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeature(db: List<MFeature>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExclusion(db: List<MExclusion>)
}