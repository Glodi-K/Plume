package com.examen.plume.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.examen.plume.data.entity.Category
import com.examen.plume.data.entity.Citation
import com.examen.plume.ui.theme.DeleteColor
import com.examen.plume.ui.theme.FavoriteColor
import com.examen.plume.ui.viewmodel.CitationViewModel
import com.examen.plume.ui.viewmodel.SortOrder
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun SwipeableItem(
    modifier: Modifier = Modifier,
    onSwipedToStart: () -> Unit = {},
    onSwipedToEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var isVisible by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = isVisible,
        exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(animationSpec = tween(300))
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
        ) {
            // background depending on drag direction
            val bgColor = when {
                offsetX > 0f -> FavoriteColor
                offsetX < 0f -> DeleteColor
                else -> MaterialTheme.colorScheme.surface
            }

            Box(modifier = Modifier
                .matchParentSize()
                .background(bgColor)
                .padding(20.dp), contentAlignment = Alignment.CenterStart
            ) {
                // icon left or right
                if (offsetX > 0f) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                } else if (offsetX < 0f) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Box(modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                val width = size.width.toFloat()
                                val threshold = width * 0.15f
                                if (abs(offsetX) > threshold) {
                                    if (offsetX < 0f) { // Balayage vers la gauche (suppression)
                                        isVisible = false
                                        onSwipedToStart()
                                    } else { // Balayage vers la droite (favori)
                                        onSwipedToEnd()
                                    }
                                }
                                offsetX = 0f
                            }
                        },
                        onDrag = { change, dragAmount ->
                            val new = (offsetX + dragAmount.x).coerceIn(-size.width.toFloat(), size.width.toFloat())
                            offsetX = new
                        }
                    )
                }
            ) {
                content()
            }
        }
    }
}

@Composable
fun CitationCard(citation: Citation) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "- ${citation.author}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            if (citation.isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = FavoriteColor,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(
    viewModel: CitationViewModel,
    selectedCategory: Category?,
    sortOrder: SortOrder
) {
    var categoryMenuExpanded by remember { mutableStateOf(false) }
    var sortMenuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Filtre par catégorie
        FilterChip(
            selected = selectedCategory != null,
            onClick = { categoryMenuExpanded = true },
            label = { Text(selectedCategory?.name ?: "Catégorie") },
            leadingIcon = {
                Icon(
                    imageVector = if (selectedCategory != null) Icons.Default.Cancel else Icons.Default.FilterList,
                    contentDescription = "Filter Icon"
                )
            }
        )

        DropdownMenu(expanded = categoryMenuExpanded, onDismissRequest = { categoryMenuExpanded = false }) {
            DropdownMenuItem(text = { Text("Toutes") }, onClick = {
                viewModel.setFilterCategory(null)
                categoryMenuExpanded = false
            })
            Category.values().forEach { category ->
                DropdownMenuItem(text = { Text(category.name) }, onClick = {
                    viewModel.setFilterCategory(category)
                    categoryMenuExpanded = false
                })
            }
        }

        // Tri par date
        FilterChip(
            selected = sortOrder != SortOrder.NONE,
            onClick = { sortMenuExpanded = true },
            label = { Text(if (sortOrder == SortOrder.NONE) "Trier par" else "Date") },
            leadingIcon = {
                Icon(
                    imageVector = when(sortOrder) {
                        SortOrder.DATE_ASC -> Icons.Default.ArrowUpward
                        SortOrder.DATE_DESC -> Icons.Default.ArrowDownward
                        else -> Icons.Default.Sort
                    },
                    contentDescription = "Sort Icon"
                )
            }
        )

        DropdownMenu(expanded = sortMenuExpanded, onDismissRequest = { sortMenuExpanded = false }) {
            DropdownMenuItem(text = { Text("Par défaut") }, onClick = {
                viewModel.setSortOrder(SortOrder.NONE)
                sortMenuExpanded = false
            })
            DropdownMenuItem(text = { Text("Plus récentes d'abord") }, onClick = {
                viewModel.setSortOrder(SortOrder.DATE_DESC)
                sortMenuExpanded = false
            })
            DropdownMenuItem(text = { Text("Plus anciennes d'abord") }, onClick = {
                viewModel.setSortOrder(SortOrder.DATE_ASC)
                sortMenuExpanded = false
            })
        }
    }
}