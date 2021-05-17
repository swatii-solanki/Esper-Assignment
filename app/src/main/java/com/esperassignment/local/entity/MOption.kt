package com.esperassignment.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

data class MOption(
    @PrimaryKey(autoGenerate = true)
    val pid: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "id") val id: String
) : Serializable
