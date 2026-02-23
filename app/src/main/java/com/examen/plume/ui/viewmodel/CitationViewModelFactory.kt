package com.examen.plume.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.examen.plume.data.repository.CitationRepository

class CitationViewModelFactory(
    private val repository: CitationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CitationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CitationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}