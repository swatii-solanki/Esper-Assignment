package com.esperassignment.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.esperassignment.local.entity.MDB
import com.esperassignment.local.entity.MExclusion

@Dao
interface DBDao {

    @Query("SELECT * from features")
    fun getFeatures(): LiveData<MDB>

    @Query("SELECT * from exclusions")
    fun getExclusions(): LiveData<List<MExclusion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeature(db: MDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExclusion(db: List<MExclusion>)
}