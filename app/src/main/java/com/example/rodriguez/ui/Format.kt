package com.example.rodriguez.ui

import com.example.rodriguez.data.Meal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

private val spanish: Locale = Locale.forLanguageTag("es")
private val calorieFormat: NumberFormat = NumberFormat.getIntegerInstance(spanish)
private val decimalFormat = DecimalFormat("0.#", DecimalFormatSymbols.getInstance(spanish))

fun Int.asCalories(): String = calorieFormat.format(this)

fun Double.asMacro(): String = decimalFormat.format(this) + " g"

fun dateLabel(date: LocalDate): String {
    val today = LocalDate.now()
    return when (date) {
        today -> "Hoy"
        today.minusDays(1) -> "Ayer"
        today.plusDays(1) -> "Mañana"
        else -> date.format(DateTimeFormatter.ofPattern("EEEE d 'de' MMMM", spanish))
            .replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(spanish) else char.toString()
            }
    }
}

fun longDateLabel(date: LocalDate): String =
    date.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", spanish))

fun mealForCurrentTime(): Meal {
    val hour = LocalTime.now().hour
    return when {
        hour < 11 -> Meal.DESAYUNO
        hour < 16 -> Meal.ALMUERZO
        hour < 21 -> Meal.CENA
        else -> Meal.SNACK
    }
}
