package com.esperassignment.local.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.esperassignment.local.converter.OptionConverter
import com.google.gson.annotations.SerializedName

data class MFeature(
    @PrimaryKey(autoGenerate = true)
    val pid: Long,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "feature_id") val feature_id: String,
    @ColumnInfo(name = "name") val name: String,
    @TypeConverters(OptionConverter::class)
    @SerializedName("options") val options: List<MOption>
)
