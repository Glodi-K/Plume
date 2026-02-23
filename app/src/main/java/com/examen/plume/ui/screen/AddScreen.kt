package com.examen.plume.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.examen.plume.data.entity.Category
import com.examen.plume.data.entity.Citation
import com.examen.plume.ui.viewmodel.CitationViewModel
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(viewModel: CitationViewModel, navController: NavController){

    var content by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(Category.MOTIVATION) }
    var isCategoryMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "Nouvelle citation",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Citation") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = author,
                        onValueChange = { author = it },
                        label = { Text("Auteur") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Sélecteur de catégorie
                    ExposedDropdownMenuBox(
                        expanded = isCategoryMenuExpanded,
                        onExpandedChange = { isCategoryMenuExpanded = !isCategoryMenuExpanded }
                    ) {
                        OutlinedTextField(
                            value = selectedCategory.name,
                            onValueChange = {}, // Lecture seule
                            readOnly = true,
                            label = { Text("Catégorie") },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier
                                .menuAnchor() // Ancre pour le menu déroulant
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        ExposedDropdownMenu(
                            expanded = isCategoryMenuExpanded,
                            onDismissRequest = { isCategoryMenuExpanded = false }
                        ) {
                            Category.values().forEach { category ->
                                DropdownMenuItem(
                                    text = { Text(category.name) },
                                    onClick = {
                                        selectedCategory = category
                                        isCategoryMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (content.isNotBlank()) {
                                viewModel.addCitation(
                                    Citation(
                                        text = content,
                                        author = author,
                                        category = selectedCategory, // Utilisation de la catégorie sélectionnée
                                        isFavorite = false
                                    )
                                )
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text("Ajouter")
                    }
                }
            }
        }
    }
}