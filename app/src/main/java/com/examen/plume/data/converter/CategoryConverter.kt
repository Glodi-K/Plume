package com.examen.plume.data.converter

import androidx.room.TypeConverter
import com.examen.plume.data.entity.Category

class CategoryConverter {

    @TypeConverter
    fun fromCategory(category: Category): String {
        return category.name
    }

    @TypeConverter
    fun toCategory(value: String): Category {
        return Category.valueOf(value)
    }
}