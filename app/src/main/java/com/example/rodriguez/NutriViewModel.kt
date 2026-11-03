package com.example.rodriguez

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.rodriguez.data.DailyStore
import com.example.rodriguez.data.Food
import com.example.rodriguez.data.FoodCatalog
import com.example.rodriguez.data.LogEntry
import com.example.rodriguez.data.Meal
import com.example.rodriguez.data.toLogEntry
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NutriViewModel(application: Application) : AndroidViewModel(application) {

    private val store = DailyStore(application)

    var goal by mutableIntStateOf(store.loadGoal())
        private set

    var selectedDate by mutableStateOf(LocalDate.now())
        private set

    val entries = mutableStateListOf<LogEntry>()
    val customFoods = mutableStateListOf<Food>()

    val allFoods: List<Food>
        get() = customFoods.asReversed() + FoodCatalog.foods

    init {
        customFoods.addAll(store.loadCustomFoods())
        reloadEntries()
    }

    val canGoNext: Boolean
        get() = selectedDate.isBefore(LocalDate.now())

    fun goToPreviousDay() {
        selectedDate = selectedDate.minusDays(1)
        reloadEntries()
    }

    fun goToNextDay() {
        if (canGoNext) {
            selectedDate = selectedDate.plusDays(1)
            reloadEntries()
        }
    }

    fun goToToday() {
        if (selectedDate != LocalDate.now()) {
            selectedDate = LocalDate.now()
            reloadEntries()
        }
    }

    fun updateGoal(newGoal: Int) {
        goal = newGoal.coerceIn(DailyStore.MIN_GOAL, DailyStore.MAX_GOAL)
        store.saveGoal(goal)
    }

    fun addEntry(food: Food, grams: Int, meal: Meal) {
        if (grams <= 0) return
        entries.add(food.toLogEntry(grams = grams, meal = meal, date = selectedDateKey()))
        persistEntries()
    }

    fun deleteEntry(entry: LogEntry) {
        if (entries.remove(entry)) {
            persistEntries()
        }
    }

    fun restoreEntry(entry: LogEntry) {
        if (entry.date == selectedDateKey() && entries.none { it.id == entry.id }) {
            entries.add(entry)
            persistEntries()
        }
    }

    fun addCustomFood(food: Food) {
        customFoods.add(food)
        store.saveCustomFoods(customFoods.toList())
    }

    fun deleteCustomFood(food: Food) {
        if (customFoods.remove(food)) {
            store.saveCustomFoods(customFoods.toList())
        }
    }

    private fun reloadEntries() {
        entries.clear()
        entries.addAll(store.loadEntries(selectedDate))
    }

    private fun persistEntries() {
        store.saveEntries(selectedDate, entries.toList())
    }

    private fun selectedDateKey(): String =
        selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
}
