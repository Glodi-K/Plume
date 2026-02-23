package com.examen.plume.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.examen.plume.data.entity.Category
import com.examen.plume.data.entity.Citation
import com.examen.plume.data.repository.CitationRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class Stats(
    val totalCitations: Int = 0,
    val totalFavorites: Int = 0,
    val mostUsedCategory: String = ""
)

enum class SortOrder {
    NONE, DATE_ASC, DATE_DESC
}

class CitationViewModel(private val repository: CitationRepository) : ViewModel() {

    private val _allCitations = MutableStateFlow<List<Citation>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filterCategory = MutableStateFlow<Category?>(null)
    val filterCategory = _filterCategory.asStateFlow()

    private val _sortOrder = MutableStateFlow(SortOrder.NONE)
    val sortOrder = _sortOrder.asStateFlow()

    val favorites: StateFlow<List<Citation>> = repository.getFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredCitations: StateFlow<List<Citation>> = combine(
        _allCitations, _searchQuery, _filterCategory, _sortOrder
    ) { citations, query, category, sort ->
        val filteredList = if (query.isBlank()) {
            citations
        } else {
            citations.filter {
                it.text.contains(query, ignoreCase = true) || it.author.contains(query, ignoreCase = true)
            }
        }.let { list ->
            if (category != null) {
                list.filter { it.category == category }
            } else {
                list
            }
        }

        when (sort) {
            SortOrder.DATE_ASC -> filteredList.sortedBy { it.createdAt }
            SortOrder.DATE_DESC -> filteredList.sortedByDescending { it.createdAt }
            SortOrder.NONE -> filteredList
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _dailyCitation = MutableStateFlow<Citation?>(null)
    val dailyCitation: StateFlow<Citation?> = _dailyCitation.asStateFlow()

    private val _stats = MutableStateFlow(Stats())
    val stats: StateFlow<Stats> = _stats.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allCitations.collect { citations ->
                if (citations.isEmpty()) {
                    // ... (données initiales)
                }
                _allCitations.value = citations
                _dailyCitation.value = citations.randomOrNull()
                _stats.value = Stats(
                    totalCitations = citations.size,
                    totalFavorites = citations.count { it.isFavorite },
                    mostUsedCategory = citations.groupingBy { it.category.name }
                        .eachCount()
                        .maxByOrNull { it.value }
                        ?.key ?: ""
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilterCategory(category: Category?) {
        _filterCategory.value = category
    }

    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
    }

    fun toggleFavorite(citation: Citation) {
        viewModelScope.launch {
            val updated = citation.copy(isFavorite = !citation.isFavorite)
            repository.updateCitation(updated)
        }
    }

    fun addCitation(citation: Citation) {
        viewModelScope.launch {
            repository.insertCitation(citation)
        }
    }

    fun deleteCitation(citation: Citation) {
        viewModelScope.launch {
            repository.deleteCitation(citation)
        }
    }
}