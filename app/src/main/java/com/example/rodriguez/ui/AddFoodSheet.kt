package com.example.rodriguez.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rodriguez.data.Food
import com.example.rodriguez.data.FoodCategory
import com.example.rodriguez.data.Meal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodSheet(
    foods: List<Food>,
    initialMeal: Meal,
    onDismiss: () -> Unit,
    onAdd: (Food, Int, Meal) -> Unit,
    onCreateCustom: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var categoryName by rememberSaveable { mutableStateOf<String?>(null) }
    var selectedFood by remember { mutableStateOf<Food?>(null) }

    val category = categoryName?.let(FoodCategory::fromName)
    val normalizedQuery = query.trim()
    val filtered = remember(foods, normalizedQuery, categoryName) {
        foods.filter { food ->
            (categoryName == null || food.category.name == categoryName) &&
                (normalizedQuery.isEmpty() || food.name.contains(normalizedQuery, ignoreCase = true))
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92f)
                .imePadding()
                .padding(horizontal = 16.dp)
        ) {
            Text("Agregar alimento", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Elige un alimento y ajusta la porción.",
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
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${filtered.size} alimentos",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Calorías por porción",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (filtered.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Text("🔎", fontSize = 40.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Sin resultados", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "Prueba con otro nombre o crea un alimento personalizado con sus calorías por porción.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = onCreateCustom) { Text("Crear alimento") }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filtered, key = { it.id }) { food ->
                        FoodRow(food = food, onClick = { selectedFood = food })
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                        )
                    }
                }
            }
        }
    }

    selectedFood?.let { food ->
        QuantityDialog(
            food = food,
            initialMeal = initialMeal,
            onDismiss = { selectedFood = null },
            onConfirm = { grams, meal ->
                selectedFood = null
                onAdd(food, grams, meal)
            }
        )
    }
}
