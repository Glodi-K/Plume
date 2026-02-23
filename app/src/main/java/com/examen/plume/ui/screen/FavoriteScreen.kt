package com.examen.plume.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examen.plume.data.entity.Citation
import com.examen.plume.ui.viewmodel.CitationViewModel

@Composable
fun FavoritesScreen(viewModel: CitationViewModel, navController: NavController) {
    val favorites by viewModel.favorites.collectAsState()

    if (favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Aucune citation favorite pour le moment.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Citations favorites",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                )
            }
            // La 'key' a été temporairement retirée pour résoudre une inférence de type
            items(favorites) { citation ->
                SwipeableItem(
                    onSwipedToEnd = { viewModel.toggleFavorite(citation) }, // Glisser pour retirer des favoris
                    onSwipedToStart = { viewModel.toggleFavorite(citation) } // Même action dans les deux sens
                ) {
                    CitationCard(citation)
                }
            }
        }
    }
}