package com.example.rodriguez.data

import java.util.UUID
import kotlin.math.roundToInt

enum class Meal(val label: String, val emoji: String) {
    DESAYUNO("Desayuno", "🌅"),
    ALMUERZO("Almuerzo", "☀️"),
    CENA("Cena", "🌙"),
    SNACK("Snack", "🍎");

    companion object {
        fun fromName(name: String?): Meal = entries.firstOrNull { it.name == name } ?: SNACK
    }
}

enum class FoodCategory(val label: String, val emoji: String) {
    FRUTAS("Frutas", "🍓"),
    VERDURAS("Verduras", "🥦"),
    CEREALES("Cereales", "🌾"),
    PROTEINAS("Proteínas", "🍗"),
    LACTEOS("Lácteos", "🥛"),
    BEBIDAS("Bebidas", "🥤"),
    SNACKS("Snacks", "🍫"),
    COMIDAS("Comidas", "🍽️"),
    GRASAS("Grasas", "🫒");

    companion object {
        fun fromName(name: String?): FoodCategory = entries.firstOrNull { it.name == name } ?: COMIDAS
    }
}

data class Food(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val emoji: String,
    val category: FoodCategory,
    val kcalPer100g: Int,
    val proteinPer100g: Double = 0.0,
    val carbsPer100g: Double = 0.0,
    val fatPer100g: Double = 0.0,
    val portionGrams: Int = 100
) {
    fun caloriesFor(grams: Int): Int = (kcalPer100g * grams / 100.0).roundToInt()

    val portionCalories: Int get() = caloriesFor(portionGrams)

    fun proteinFor(grams: Int): Double = proteinPer100g * grams / 100.0

    fun carbsFor(grams: Int): Double = carbsPer100g * grams / 100.0

    fun fatFor(grams: Int): Double = fatPer100g * grams / 100.0
}

data class LogEntry(
    val id: String = UUID.randomUUID().toString(),
    val date: String,
    val foodId: String,
    val name: String,
    val emoji: String,
    val grams: Int,
    val meal: Meal,
    val kcalPer100g: Int,
    val proteinPer100g: Double,
    val carbsPer100g: Double,
    val fatPer100g: Double
) {
    val calories: Int get() = (kcalPer100g * grams / 100.0).roundToInt()

    val protein: Double get() = proteinPer100g * grams / 100.0

    val carbs: Double get() = carbsPer100g * grams / 100.0

    val fat: Double get() = fatPer100g * grams / 100.0
}

data class DayTotals(
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double
)

fun List<LogEntry>.totals(): DayTotals = DayTotals(
    calories = sumOf { it.calories },
    protein = sumOf { it.protein },
    carbs = sumOf { it.carbs },
    fat = sumOf { it.fat }
)

fun Food.toLogEntry(grams: Int, meal: Meal, date: String): LogEntry = LogEntry(
    date = date,
    foodId = id,
    name = name,
    emoji = emoji,
    grams = grams,
    meal = meal,
    kcalPer100g = kcalPer100g,
    proteinPer100g = proteinPer100g,
    carbsPer100g = carbsPer100g,
    fatPer100g = fatPer100g
)
