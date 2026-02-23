package com.examen.plume

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.examen.plume.data.database.AppDatabase
import com.examen.plume.data.repository.CitationRepository
import com.examen.plume.navigation.NavGraph
import com.examen.plume.navigation.PlumeBottomBar
import com.examen.plume.ui.viewmodel.CitationViewModel
import com.examen.plume.ui.viewmodel.CitationViewModelFactory
import com.examen.plume.ui.theme.PlumeTheme
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.examen.plume.ui.viewmodel.ThemeMode
import com.examen.plume.ui.viewmodel.ThemeViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import com.examen.plume.R

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: CitationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        // Création de la DB et Repository
        val db = AppDatabase.getDatabase(this)
        val repository = CitationRepository(db.citationDao())

        // Création du ViewModel via Factory
        val factory = CitationViewModelFactory(repository)
        viewModel = factory.create(CitationViewModel::class.java)

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val currentThemeMode = themeViewModel.themeMode.collectAsState().value

            PlumeTheme(
                themeMode = currentThemeMode
            ) {

                val navController = rememberNavController()
                val currentBackStackEntry =
                    navController.currentBackStackEntryAsState()
                val currentRoute =
                    currentBackStackEntry.value?.destination?.route

                @OptIn(ExperimentalMaterial3Api::class)
                Scaffold(
                    topBar = {
                        // Barre supérieure personnalisée avec uniquement le logo centré
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Logique robuste pour le choix du logo
                            val isDark = when (currentThemeMode) {
                                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                                ThemeMode.DARK -> true
                                ThemeMode.LIGHT -> false
                            }

                            val logoResId = if (isDark) {
                                R.drawable.plume_icon_white_nobg
                            } else {
                                R.drawable.plume_icon_black_nobg
                            }

                            Icon(
                                painter = painterResource(id = logoResId),
                                contentDescription = "Plume Logo",
                                modifier = Modifier.size(40.dp),
                                tint = Color.Unspecified // Utilise la couleur du drawable
                            )
                        }
                    },
                    bottomBar = {
                        PlumeBottomBar(
                            currentRoute = currentRoute,
                            onNavigate = { route ->
                                navController.navigate(route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->

                    NavGraph(
                        navController = navController,
                        repository = repository,
                        themeViewModel = themeViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}