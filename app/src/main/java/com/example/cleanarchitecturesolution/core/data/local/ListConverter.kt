package com.example.cleanarchitecturesolution.core.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun toJson(data: List<String>): String = Gson().toJson(data)

        @TypeConverter
        @JvmStatic
        fun fromJson(json: String): List<String> =
            Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    }
}
