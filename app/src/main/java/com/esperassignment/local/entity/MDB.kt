package com.esperassignment.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esperassignment.local.converter.FeatureConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "features")
data class MDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @TypeConverters(FeatureConverter::class)
    @SerializedName("features") val features: List<MFeature>,
)
