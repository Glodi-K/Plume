package com.examen.plume.data.dao

import androidx.room.*
import com.examen.plume.data.entity.Citation
import kotlinx.coroutines.flow.Flow

@Dao
interface CitationDao {

    @Query("SELECT * FROM citation")
    fun getAllCitations(): Flow<List<Citation>>

    @Query("SELECT * FROM citation WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Citation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCitation(citation: Citation)

    @Update
    suspend fun updateCitation(citation: Citation)

    @Delete
    suspend fun deleteCitation(citation: Citation)
}