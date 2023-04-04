package com.example.dummyproducts.data.database.products.convertors

import androidx.room.TypeConverter
import java.util.Arrays
import java.util.stream.Collectors

class ProductImagesConvertor {
    companion object {
        private const val COLLECTOR_STR = ","
    }

    @TypeConverter
    fun getStrFromListImages(images: List<String>): String {
        return images.stream().collect(Collectors.joining(COLLECTOR_STR))
    }

    @TypeConverter
    fun getListImagesFromStr(str: String): List<String> {
        return str.split(COLLECTOR_STR)
    }
}