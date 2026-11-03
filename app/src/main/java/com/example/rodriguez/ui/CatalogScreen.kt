package com.example.rodriguez.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rodriguez.data.Food
import com.example.rodriguez.data.FoodCategory
import com.example.rodriguez.data.Meal

@Composable
fun CatalogScreen(
    foods: List<Food>,
    customIds: Set<String>,
    onAdd: (Food, Int, Meal) -> Unit,
    onCreateCustom: () -> Unit,
    onDeleteCustom: (Food) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by rememberSaveable { mutableStateOf("") }
    var categoryName by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }
    var deleteTarget by remember { mutableStateOf<Food?>(null) }

    val category = categoryName?.let(FoodCategory::fromName)
    val normalizedQuery = query.trim()
    val filtered = remember(foods, normalizedQuery, categoryName) {
        foods.filter { food ->
            (categoryName == null || food.category.name == categoryName) &&
                (normalizedQuery.isEmpty() || food.name.contains(normalizedQuery, ignoreCase = true))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Alimentos",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "${foods.size} alimentos · calorías por porción",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { input -> query = input },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar alimento...") },
            leadingIcon = { Text("🔍") },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    TextButton(onClick = { query = "" }) { Text("✕") }
                }
            },
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        CategoryFilterChips(
            selected = category,
            onSelect = { selected -> categoryName = selected?.name }
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "${filtered.size} resultados",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Toca para agregar",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.height(8.dp))

        if (filtered.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔎", fontSize = 40.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Sin resultados", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Prueba con otro nombre o agrega tu propio alimento.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = onCreateCustom) { Text("Crear alimento") }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filtered, key = { it.id }) { food ->
                    CatalogFoodCard(
                        food = food,
                        isCustom = food.id in customIds,
                        onClick = { selectedFood = food },
                        onDelete = { deleteTarget = food }
                    )
                }
            }
        }
    }

    selectedFood?.let { food ->
        QuantityDialog(
            food = food,
            initialMeal = mealForCurrentTime(),
            onDismiss = { selectedFood = null },
            onConfirm = { grams, meal ->
                selectedFood = null
                onAdd(food, grams, meal)
            }
        )
    }

    deleteTarget?.let { food ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            title = { Text("Eliminar alimento") },
            text = {
                Text(
                    "¿Quieres quitar «${food.name}» del catálogo? " +
                        "Los registros de tu diario se conservan."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteCustom(food)
                        deleteTarget = null
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteTarget = null }) { Text("Cancelar") }
            }
        )
    }
}

@Composable
private fun CatalogFoodCard(
    food: Food,
    isCustom: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(food.emoji, fontSize = 22.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = food.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (isCustom) {
                        Spacer(Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Text(
                                text = "Personalizado",
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = "${food.portionGrams} g · ${food.category.label}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${food.kcalPer100g.asCalories()} kcal / 100 g",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = food.portionCalories.asCalories(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "kcal / porción",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (isCustom) {
                Spacer(Modifier.width(2.dp))
                IconButton(onClick = onDelete, modifier = Modifier.size(34.dp)) {
                    Text("🗑️", fontSize = 14.sp)
                }
            }
        }
    }
}
