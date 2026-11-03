package com.example.rodriguez

import com.example.rodriguez.data.Food
import com.example.rodriguez.data.FoodCategory
import com.example.rodriguez.data.LogEntry
import com.example.rodriguez.data.Meal
import com.example.rodriguez.data.totals
import org.junit.Assert.assertEquals
import org.junit.Test

class CalorieMathTest {

    @Test
    fun portionCaloriesAreCalculatedPerGrams() {
        val rice = Food(
            name = "Arroz blanco cocido",
            emoji = "🍚",
            category = FoodCategory.CEREALES,
            kcalPer100g = 130,
            portionGrams = 150
        )

        assertEquals(195, rice.portionCalories)
        assertEquals(65, rice.caloriesFor(50))
    }

    @Test
    fun dailyTotalsAddEntriesAndMacros() {
        val chicken = LogEntry(
            date = "2026-10-29",
            foodId = "chicken",
            name = "Pechuga de pollo",
            emoji = "🍗",
            grams = 150,
            meal = Meal.ALMUERZO,
            kcalPer100g = 165,
            proteinPer100g = 31.0,
            carbsPer100g = 0.0,
            fatPer100g = 3.6
        )
        val rice = LogEntry(
            date = "2026-10-29",
            foodId = "rice",
            name = "Arroz blanco cocido",
            emoji = "🍚",
            grams = 150,
            meal = Meal.ALMUERZO,
            kcalPer100g = 130,
            proteinPer100g = 2.7,
            carbsPer100g = 28.2,
            fatPer100g = 0.3
        )

        val totals = listOf(chicken, rice).totals()

        assertEquals(443, totals.calories)
        assertEquals(50.55, totals.protein, 0.001)
        assertEquals(42.3, totals.carbs, 0.001)
        assertEquals(5.85, totals.fat, 0.001)
    }
}
