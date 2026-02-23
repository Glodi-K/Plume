package com.examen.plume.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.unit.dp

@Composable
fun PlumeBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        tonalElevation = 6.dp
    ) {

        // Home
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Accueil") },
            label = { Text("Accueil") },
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        // Add
        NavigationBarItem(
            selected = currentRoute == "add",
            onClick = { onNavigate("add") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Ajouter") },
            label = { Text("Ajouter") },
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        // Favoris
        NavigationBarItem(
            selected = currentRoute == "favorites",
            onClick = { onNavigate("favorites") },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoris") },
            label = { Text("Favoris") },
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        // Profil
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") },
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profil") },
            label = { Text("Profil") },
            alwaysShowLabel = false,
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )
    }
}