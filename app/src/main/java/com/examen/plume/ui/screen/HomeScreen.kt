package com.examen.plume.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examen.plume.data.entity.Citation
import com.examen.plume.ui.viewmodel.CitationViewModel
import com.examen.plume.ui.viewmodel.SortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: CitationViewModel,
    navController: NavController
) {

    val dailyCitation by viewModel.dailyCitation.collectAsState()
    val citations by viewModel.filteredCitations.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filterCategory by viewModel.filterCategory.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 🔹 Barre de recherche
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Rechercher une citation...") },
                singleLine = true,
                shape = RoundedCornerShape(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                )
            )
        }

        // 🔹 Section des filtres
        item {
            FilterChips(viewModel, filterCategory, sortOrder)
        }

        // 🔹 Citation du jour
        item {
            Text(
                text = "Citation du jour",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            dailyCitation?.let { citation ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "\"${citation.text}\"",
                            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "- ${citation.author}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // 🔹 Liste des citations
        item {
            Text(
                text = "Toutes les citations",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
        items(citations, key = { it.id }) { citation ->
            SwipeableItem(
                onSwipedToStart = { viewModel.deleteCitation(citation) },
                onSwipedToEnd = { viewModel.toggleFavorite(citation) }
            ) {
                CitationCard(citation)
            }
        }
    }
}