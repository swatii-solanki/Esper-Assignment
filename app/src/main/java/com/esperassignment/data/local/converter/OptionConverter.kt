package com.esperassignment.data.local.converter

import androidx.room.TypeConverter
import com.esperassignment.data.local.entity.MOption
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class OptionConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToOptionList(data: String?): List<MOption?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<MOption?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun optionListToString(someObjects: List<MOption?>?): String? {
        return gson.toJson(someObjects)
    }
}