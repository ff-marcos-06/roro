package com.example.rodriguez.data

import android.content.Context
import androidx.core.content.edit
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DailyStore(context: Context) {

    private val prefs =
        context.applicationContext.getSharedPreferences("nutrical_store", Context.MODE_PRIVATE)

    fun loadGoal(): Int = prefs.getInt(KEY_GOAL, DEFAULT_GOAL).coerceAtLeast(MIN_GOAL)

    fun saveGoal(goal: Int) {
        prefs.edit { putInt(KEY_GOAL, goal.coerceIn(MIN_GOAL, MAX_GOAL)) }
    }

    fun loadEntries(date: LocalDate): List<LogEntry> {
        val raw = prefs.getString(entriesKey(date), null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            List(array.length()) { index -> array.getJSONObject(index).toLogEntry() }
        }.getOrDefault(emptyList())
    }

    fun saveEntries(date: LocalDate, entries: List<LogEntry>) {
        val array = JSONArray()
        entries.forEach { array.put(it.toJson()) }
        prefs.edit { putString(entriesKey(date), array.toString()) }
    }

    fun loadCustomFoods(): List<Food> {
        val raw = prefs.getString(KEY_CUSTOM_FOODS, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            List(array.length()) { index -> array.getJSONObject(index).toFood() }
        }.getOrDefault(emptyList())
    }

    fun saveCustomFoods(foods: List<Food>) {
        val array = JSONArray()
        foods.forEach { array.put(it.toJson()) }
        prefs.edit { putString(KEY_CUSTOM_FOODS, array.toString()) }
    }

    private fun entriesKey(date: LocalDate): String =
        "entries_" + date.format(DateTimeFormatter.ISO_LOCAL_DATE)

    private fun LogEntry.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("date", date)
        put("foodId", foodId)
        put("name", name)
        put("emoji", emoji)
        put("grams", grams)
        put("meal", meal.name)
        put("kcalPer100g", kcalPer100g)
        put("proteinPer100g", proteinPer100g)
        put("carbsPer100g", carbsPer100g)
        put("fatPer100g", fatPer100g)
    }

    private fun JSONObject.toLogEntry(): LogEntry = LogEntry(
        id = optString("id"),
        date = optString("date"),
        foodId = optString("foodId"),
        name = optString("name"),
        emoji = optString("emoji", "🍽️"),
        grams = optInt("grams", 100),
        meal = Meal.fromName(optString("meal")),
        kcalPer100g = optInt("kcalPer100g"),
        proteinPer100g = optDouble("proteinPer100g"),
        carbsPer100g = optDouble("carbsPer100g"),
        fatPer100g = optDouble("fatPer100g")
    )

    private fun Food.toJson(): JSONObject = JSONObject().apply {
        put("id", id)
        put("name", name)
        put("emoji", emoji)
        put("category", category.name)
        put("kcalPer100g", kcalPer100g)
        put("proteinPer100g", proteinPer100g)
        put("carbsPer100g", carbsPer100g)
        put("fatPer100g", fatPer100g)
        put("portionGrams", portionGrams)
    }

    private fun JSONObject.toFood(): Food = Food(
        id = optString("id"),
        name = optString("name"),
        emoji = optString("emoji", "🍽️"),
        category = FoodCategory.fromName(optString("category")),
        kcalPer100g = optInt("kcalPer100g"),
        proteinPer100g = optDouble("proteinPer100g"),
        carbsPer100g = optDouble("carbsPer100g"),
        fatPer100g = optDouble("fatPer100g"),
        portionGrams = optInt("portionGrams", 100)
    )

    companion object {
        const val DEFAULT_GOAL = 2000
        const val MIN_GOAL = 800
        const val MAX_GOAL = 6000
        private const val KEY_GOAL = "daily_goal"
        private const val KEY_CUSTOM_FOODS = "custom_foods"
    }
}
