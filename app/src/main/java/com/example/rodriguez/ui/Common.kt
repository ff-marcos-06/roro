package com.example.rodriguez.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rodriguez.data.DailyStore
import com.example.rodriguez.data.Food
import com.example.rodriguez.data.FoodCategory
import com.example.rodriguez.data.Meal
import kotlin.math.roundToInt

@Composable
fun CategoryFilterChips(
    selected: FoodCategory?,
    onSelect: (FoodCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(key = "all") {
            FilterChip(
                selected = selected == null,
                onClick = { onSelect(null) },
                label = { Text("Todos") }
            )
        }
        items(FoodCategory.entries.toList(), key = { it.name }) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(category) },
                label = { Text("${category.emoji} ${category.label}") }
            )
        }
    }
}

@Composable
fun MacroBadge(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(7.dp).background(color, CircleShape))
                Spacer(Modifier.width(5.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun FoodRow(
    food: Food,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(food.emoji, fontSize = 22.sp)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(food.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${food.portionGrams} g · ${food.portionCalories.asCalories()} kcal por porción",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${food.kcalPer100g.asCalories()} kcal por 100 g",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
            )
        }
        Spacer(Modifier.width(8.dp))
        if (trailing == null) {
            Text(
                text = "＋",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            trailing()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuantityDialog(
    food: Food,
    initialMeal: Meal,
    onDismiss: () -> Unit,
    onConfirm: (grams: Int, meal: Meal) -> Unit
) {
    var gramsText by remember(food.id) { mutableStateOf(food.portionGrams.toString()) }
    var mealName by remember(food.id, initialMeal.name) { mutableStateOf(initialMeal.name) }

    val grams = gramsText.toIntOrNull()
    val meal = Meal.fromName(mealName)
    val previewCalories = if (grams != null && grams > 0) food.caloriesFor(grams) else 0
    val presets = listOf("½" to 0.5, "1" to 1.0, "1½" to 1.5, "2" to 2.0)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(food.emoji, fontSize = 28.sp)
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(food.name, style = MaterialTheme.typography.titleLarge)
                    Text(
                        text = "${food.portionGrams} g por porción",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = gramsText,
                    onValueChange = { input -> gramsText = input.filter(Char::isDigit).take(4) },
                    label = { Text("Cantidad") },
                    suffix = { Text("g") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Porciones rápidas",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    presets.forEach { (label, multiplier) ->
                        AssistChip(
                            onClick = {
                                gramsText = (food.portionGrams * multiplier).roundToInt().toString()
                            },
                            label = { Text("$label ×") }
                        )
                    }
                }
                Text(
                    text = "Comida",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Meal.entries.forEach { option ->
                        FilterChip(
                            selected = meal == option,
                            onClick = { mealName = option.name },
                            label = { Text("${option.emoji} ${option.label}") }
                        )
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                text = "Calorías de esta porción",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                            Text(
                                text = "${previewCalories.asCalories()} kcal",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            if (grams != null && grams > 0) {
                                Text(
                                    text = "P ${food.proteinFor(grams).asMacro()} · C ${food.carbsFor(grams).asMacro()} · G ${food.fatFor(grams).asMacro()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            }
                        }
                        Text(food.emoji, fontSize = 34.sp)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val value = grams
                    if (value != null && value > 0) onConfirm(value, meal)
                },
                enabled = grams != null && grams in 1..5000
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GoalDialog(
    goal: Int,
    onDismiss: () -> Unit,
    onSave: (Int) -> Unit
) {
    var goalText by remember { mutableStateOf(goal.toString()) }
    val parsed = goalText.toIntOrNull()
    val valid = parsed != null && parsed in DailyStore.MIN_GOAL..DailyStore.MAX_GOAL

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Meta diaria") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Define cuántas calorías quieres consumir cada día.",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(
                    value = goalText,
                    onValueChange = { input -> goalText = input.filter(Char::isDigit).take(4) },
                    label = { Text("Calorías por día") },
                    suffix = { Text("kcal") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(1500, 1800, 2000, 2200, 2500).forEach { value ->
                        AssistChip(
                            onClick = { goalText = value.toString() },
                            label = { Text(value.asCalories()) }
                        )
                    }
                }
                Text(
                    text = "Rango permitido: ${DailyStore.MIN_GOAL.asCalories()} – ${DailyStore.MAX_GOAL.asCalories()} kcal",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    parsed?.let { value ->
                        if (value in DailyStore.MIN_GOAL..DailyStore.MAX_GOAL) onSave(value)
                    }
                },
                enabled = valid
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomFoodDialog(
    onDismiss: () -> Unit,
    onSave: (Food) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var emoji by rememberSaveable { mutableStateOf("") }
    var kcalText by rememberSaveable { mutableStateOf("") }
    var portionText by rememberSaveable { mutableStateOf("100") }
    var proteinText by rememberSaveable { mutableStateOf("") }
    var carbsText by rememberSaveable { mutableStateOf("") }
    var fatText by rememberSaveable { mutableStateOf("") }
    var categoryName by rememberSaveable { mutableStateOf(FoodCategory.COMIDAS.name) }

    val kcal = kcalText.toIntOrNull()
    val portion = portionText.toIntOrNull()
    val category = FoodCategory.fromName(categoryName)
    val valid = name.isNotBlank() && kcal != null && kcal in 1..900 &&
        portion != null && portion in 1..2000
    val estimated = if (kcal != null && portion != null) {
        (kcal * portion / 100.0).roundToInt()
    } else {
        0
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nuevo alimento") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Las calorías y macronutrientes se registran por cada 100 g.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { input -> name = input.take(60) },
                    label = { Text("Nombre") },
                    placeholder = { Text("Ej. Ensalada de la casa") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = emoji,
                        onValueChange = { input -> emoji = input.take(4) },
                        label = { Text("Emoji") },
                        placeholder = { Text("🍽️") },
                        singleLine = true,
                        modifier = Modifier.width(96.dp)
                    )
                    OutlinedTextField(
                        value = kcalText,
                        onValueChange = { input -> kcalText = input.filter(Char::isDigit).take(3) },
                        label = { Text("kcal / 100 g") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = portionText,
                    onValueChange = { input -> portionText = input.filter(Char::isDigit).take(4) },
                    label = { Text("Porción habitual (g)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Categoría", style = MaterialTheme.typography.labelLarge)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FoodCategory.entries.forEach { option ->
                        FilterChip(
                            selected = category == option,
                            onClick = { categoryName = option.name },
                            label = { Text(option.label) }
                        )
                    }
                }
                Text("Macros por 100 g (opcional)", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MacroField(
                        value = proteinText,
                        onValueChange = { proteinText = it },
                        label = "Prot.",
                        modifier = Modifier.weight(1f)
                    )
                    MacroField(
                        value = carbsText,
                        onValueChange = { carbsText = it },
                        label = "Carb.",
                        modifier = Modifier.weight(1f)
                    )
                    MacroField(
                        value = fatText,
                        onValueChange = { fatText = it },
                        label = "Grasa",
                        modifier = Modifier.weight(1f)
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Porción de ${portion ?: 0} g",
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${estimated.asCalories()} kcal",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = valid,
                onClick = {
                    if (kcal != null && portion != null) {
                        onSave(
                            Food(
                                name = name.trim(),
                                emoji = emoji.trim().ifBlank { "🍽️" },
                                category = category,
                                kcalPer100g = kcal,
                                proteinPer100g = proteinText.toMacroOrZero(),
                                carbsPer100g = carbsText.toMacroOrZero(),
                                fatPer100g = fatText.toMacroOrZero(),
                                portionGrams = portion
                            )
                        )
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Composable
private fun MacroField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            onValueChange(input.filter { it.isDigit() || it == '.' || it == ',' }.take(5))
        },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = modifier
    )
}

private fun String.toMacroOrZero(): Double =
    trim().replace(',', '.').toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
