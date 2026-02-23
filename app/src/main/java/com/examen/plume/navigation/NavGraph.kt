package com.examen.plume.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import com.examen.plume.ui.screen.HomeScreen
import com.examen.plume.ui.screen.AddScreen
import com.examen.plume.ui.screen.FavoritesScreen
import com.examen.plume.ui.screen.ProfileScreen
import com.examen.plume.ui.viewmodel.CitationViewModel
import com.examen.plume.ui.viewmodel.CitationViewModelFactory
import com.examen.plume.data.repository.CitationRepository
import com.examen.plume.ui.viewmodel.ThemeViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    repository: CitationRepository,
    themeViewModel: ThemeViewModel,
    modifier: Modifier = Modifier
) {

    val viewModel: CitationViewModel = viewModel(
        factory = CitationViewModelFactory(repository)
    )

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth / 2 },
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth / 2 },
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(400)
            ) + fadeOut(animationSpec = tween(400))
        }
    ){

        composable("home") {
            HomeScreen(viewModel, navController)
        }

        composable("add") {
            AddScreen(viewModel, navController)
        }

        composable("favorites") {
            FavoritesScreen(viewModel, navController)
        }

        composable("profile") {
            ProfileScreen(viewModel, navController, themeViewModel)
        }
    }
}