package lk.chamiviews.tablepay.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import lk.chamiviews.tablepay.data.model.LocalProduct

class Converters {
    @TypeConverter
    fun fromProductList(products: List<LocalProduct>): String {
        return Gson().toJson(products)
    }

    @TypeConverter
    fun toProductList(json: String): List<LocalProduct> {
        val type = object : TypeToken<List<LocalProduct>>() {}.type
        return Gson().fromJson(json, type)
    }
}