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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rodriguez.data.DayTotals
import com.example.rodriguez.data.LogEntry
import com.example.rodriguez.data.Meal
import com.example.rodriguez.data.totals
import com.example.rodriguez.ui.theme.CarbsColor
import com.example.rodriguez.ui.theme.FatColor
import com.example.rodriguez.ui.theme.NutriCalTheme
import com.example.rodriguez.ui.theme.ProteinColor
import java.time.LocalDate

@Composable
fun HomeScreen(
    date: LocalDate,
    goal: Int,
    entries: List<LogEntry>,
    canGoNext: Boolean,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onToday: () -> Unit,
    onEditGoal: () -> Unit,
    onAddToMeal: (Meal) -> Unit,
    onDeleteEntry: (LogEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val totals = entries.totals()

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "header") {
            HomeHeader(onEditGoal = onEditGoal)
        }
        item(key = "date") {
            DateSelector(
                date = date,
                canGoNext = canGoNext,
                onPreviousDay = onPreviousDay,
                onNextDay = onNextDay,
                onToday = onToday
            )
        }
        item(key = "summary") {
            SummaryCard(goal = goal, totals = totals)
        }
        if (entries.isEmpty()) {
            item(key = "empty") {
                StarterCard()
            }
        }
        Meal.entries.forEach { meal ->
            item(key = meal.name) {
                MealSection(
                    meal = meal,
                    entries = entries.filter { it.meal == meal },
                    onAdd = { onAddToMeal(meal) },
                    onDeleteEntry = onDeleteEntry
                )
            }
        }
    }
}

@Composable
private fun HomeHeader(onEditGoal: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = "NutriCal",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Control de calorías diario",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        FilledTonalIconButton(onClick = onEditGoal) {
            Text("⚙️", fontSize = 18.sp)
        }
    }
}

@Composable
private fun DateSelector(
    date: LocalDate,
    canGoNext: Boolean,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onToday: () -> Unit
) {
    val isToday = date == LocalDate.now()

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPreviousDay) {
                    Text("‹", fontSize = 28.sp, color = MaterialTheme.colorScheme.onSurface)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(dateLabel(date), style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = longDateLabel(date),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onNextDay, enabled = canGoNext) {
                    Text(
                        text = "›",
                        fontSize = 28.sp,
                        color = if (canGoNext) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        }
                    )
                }
            }
            if (!isToday) {
                TextButton(
                    onClick = onToday,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Volver a hoy")
                }
            }
        }
    }
}

@Composable
private fun SummaryCard(goal: Int, totals: DayTotals) {
    val consumed = totals.calories
    val isOver = consumed > goal
    val progress = if (goal > 0) (consumed.toFloat() / goal).coerceIn(0f, 1f) else 0f
    val remaining = (goal - consumed).coerceAtLeast(0)
    val over = (consumed - goal).coerceAtLeast(0)
    val ringColor = if (isOver) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    val mainValue = if (isOver) over else remaining

    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Resumen del día",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Meta ${goal.asCalories()} kcal",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
            Spacer(Modifier.height(12.dp))
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(172.dp),
                    color = ringColor,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                    strokeWidth = 14.dp,
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = mainValue.asCalories(),
                        style = MaterialTheme.typography.displaySmall,
                        color = if (isOver) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = if (isOver) "kcal excedidas" else "kcal restantes",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${consumed.asCalories()} de ${goal.asCalories()} kcal",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MacroBadge(
                    label = "Prot.",
                    value = totals.protein.asMacro(),
                    color = ProteinColor,
                    modifier = Modifier.weight(1f)
                )
                MacroBadge(
                    label = "Carb.",
                    value = totals.carbs.asMacro(),
                    color = CarbsColor,
                    modifier = Modifier.weight(1f)
                )
                MacroBadge(
                    label = "Grasa",
                    value = totals.fat.asMacro(),
                    color = FatColor,
                    modifier = Modifier.weight(1f)
                )
            }
            if (isOver) {
                Spacer(Modifier.height(10.dp))
                Text(
                    text = "Superaste tu meta diaria",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun StarterCard() {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🥗", fontSize = 30.sp)
            Spacer(Modifier.width(12.dp))
            Column {
                Text("Empieza tu día", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Toca ＋ en una comida para registrar alimentos y ver sus calorías por porción.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun MealSection(
    meal: Meal,
    entries: List<LogEntry>,
    onAdd: () -> Unit,
    onDeleteEntry: (LogEntry) -> Unit
) {
    val mealCalories = entries.sumOf { it.calories }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(meal.emoji, fontSize = 20.sp)
            Spacer(Modifier.width(8.dp))
            Text(meal.label, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.weight(1f))
            Text(
                text = "${mealCalories.asCalories()} kcal",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.width(4.dp))
            FilledTonalIconButton(
                onClick = onAdd,
                modifier = Modifier.size(36.dp)
            ) {
                Text("＋", fontSize = 18.sp)
            }
        }
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 1.dp
        ) {
            if (entries.isEmpty()) {
                Text(
                    text = "Sin alimentos registrados",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
            } else {
                Column {
                    entries.forEachIndexed { index, entry ->
                        EntryRow(entry = entry, onDelete = { onDeleteEntry(entry) })
                        if (index != entries.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EntryRow(entry: LogEntry, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(entry.emoji, fontSize = 20.sp)
        }
        Spacer(Modifier.width(10.dp))
        Column(Modifier.weight(1f)) {
            Text(entry.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${entry.grams} g · ${entry.calories.asCalories()} kcal",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(onClick = onDelete, modifier = Modifier.size(38.dp)) {
            Text("🗑️", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
private fun HomeScreenPreview() {
    val today = LocalDate.now()
    NutriCalTheme {
        HomeScreen(
            date = today,
            goal = 2000,
            entries = listOf(
                LogEntry(
                    date = today.toString(),
                    foodId = "1",
                    name = "Avena",
                    emoji = "🥣",
                    grams = 40,
                    meal = Meal.DESAYUNO,
                    kcalPer100g = 389,
                    proteinPer100g = 16.9,
                    carbsPer100g = 66.3,
                    fatPer100g = 6.9
                ),
                LogEntry(
                    date = today.toString(),
                    foodId = "2",
                    name = "Pechuga de pollo",
                    emoji = "🍗",
                    grams = 150,
                    meal = Meal.ALMUERZO,
                    kcalPer100g = 165,
                    proteinPer100g = 31.0,
                    carbsPer100g = 0.0,
                    fatPer100g = 3.6
                ),
                LogEntry(
                    date = today.toString(),
                    foodId = "3",
                    name = "Arroz blanco cocido",
                    emoji = "🍚",
                    grams = 150,
                    meal = Meal.ALMUERZO,
                    kcalPer100g = 130,
                    proteinPer100g = 2.7,
                    carbsPer100g = 28.2,
                    fatPer100g = 0.3
                )
            ),
            canGoNext = false,
            onPreviousDay = {},
            onNextDay = {},
            onToday = {},
            onEditGoal = {},
            onAddToMeal = {},
            onDeleteEntry = {}
        )
    }
}
