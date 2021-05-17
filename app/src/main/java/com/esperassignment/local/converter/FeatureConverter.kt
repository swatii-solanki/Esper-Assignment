package com.esperassignment.local.converter

import androidx.room.TypeConverter
import com.esperassignment.local.entity.MFeature
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class FeatureConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToFeatureList(data: String?): List<MFeature?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<MFeature?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun featureListToString(someObjects: List<MFeature?>?): String? {
        return gson.toJson(someObjects)
    }
}