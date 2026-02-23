package com.examen.plume.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citation")
data class Citation(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val author: String,
    val category: Category,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)