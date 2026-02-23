package com.examen.plume.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examen.plume.ui.viewmodel.CitationViewModel
import com.examen.plume.ui.viewmodel.ThemeMode
import com.examen.plume.ui.viewmodel.ThemeViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.ui.Alignment

@Composable
fun ProfileScreen(viewModel: CitationViewModel, navController: NavController, themeViewModel: ThemeViewModel) {
    val stats by viewModel.stats.collectAsState()
    val currentTheme = themeViewModel.themeMode.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Statistiques", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nombre total de citations : ${stats.totalCitations}")
                    Text("Nombre de favoris : ${stats.totalFavorites}")
                    Text("Catégorie la plus utilisée : ${stats.mostUsedCategory}")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Thème",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            ThemeOption(
                text = "Système",
                icon = Icons.Default.BrightnessAuto,
                selected = currentTheme == ThemeMode.SYSTEM
            ) {
                themeViewModel.setTheme(ThemeMode.SYSTEM)
            }
        }

        item {
            ThemeOption(
                text = "Clair",
                icon = Icons.Default.Brightness5,
                selected = currentTheme == ThemeMode.LIGHT
            ) {
                themeViewModel.setTheme(ThemeMode.LIGHT)
            }
        }

        item {
            ThemeOption(
                text = "Sombre",
                icon = Icons.Default.Brightness4,
                selected = currentTheme == ThemeMode.DARK
            ) {
                themeViewModel.setTheme(ThemeMode.DARK)
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Aide",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            InfoItem(
                text = "Contactez-nous",
                icon = Icons.Default.ContactSupport
            ) {
                // TODO: Naviguer vers un écran de contact ou ouvrir un client de messagerie
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "À propos",
                style = MaterialTheme.typography.titleLarge
            )
        }

        item {
            InfoItem(
                text = "À propos de l'application",
                icon = Icons.Default.Info
            ) {
                // TODO: Naviguer vers un écran "À propos"
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeOption(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        ListItem(
            headlineContent = { Text(text) },
            leadingContent = {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                )
            },
            trailingContent = {
                if (selected) {
                    Icon(
                        imageVector = Icons.Default.RadioButtonChecked,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Not Selected"
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        ListItem(
            headlineContent = { Text(text) },
            leadingContent = {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                )
            }
        )
    }
}
