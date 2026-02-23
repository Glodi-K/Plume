package com.examen.plume.data.repository

import com.examen.plume.data.dao.CitationDao
import com.examen.plume.data.entity.Citation
import kotlinx.coroutines.flow.Flow

class CitationRepository(private val citationDao: CitationDao) {

    val allCitations: Flow<List<Citation>> = citationDao.getAllCitations()

    fun getFavorites(): Flow<List<Citation>> = citationDao.getFavorites()

    suspend fun insertCitation(citation: Citation) = citationDao.insertCitation(citation)

    suspend fun updateCitation(citation: Citation) = citationDao.updateCitation(citation)

    suspend fun deleteCitation(citation: Citation) = citationDao.deleteCitation(citation)
}