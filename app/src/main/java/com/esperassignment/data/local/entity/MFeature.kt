package com.esperassignment.data.local.entity

import androidx.room.*
import com.esperassignment.data.local.converter.OptionConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "features",indices = [Index(value = ["feature_id"], unique = true)])
data class MFeature(
    @PrimaryKey(autoGenerate = true)
    val pid: Long,
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "feature_id") val feature_id: String,
    @ColumnInfo(name = "name") val name: String,
    @TypeConverters(OptionConverter::class)
    @SerializedName("options") val options: List<MOption>
)
