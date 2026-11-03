package com.example.rodriguez.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rodriguez.NutriViewModel
import com.example.rodriguez.data.Meal
import kotlinx.coroutines.launch

@Composable
fun NutriCalApp(viewModel: NutriViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var showAddSheet by rememberSaveable { mutableStateOf(false) }
    var addSheetMealName by rememberSaveable { mutableStateOf(mealForCurrentTime().name) }
    var showGoalDialog by rememberSaveable { mutableStateOf(false) }
    var showCustomFoodDialog by rememberSaveable { mutableStateOf(false) }

    fun showMessage(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Text("🍽️", fontSize = 20.sp) },
                    label = { Text("Diario") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Text("📚", fontSize = 20.sp) },
                    label = { Text("Alimentos") }
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    if (selectedTab == 0) {
                        addSheetMealName = mealForCurrentTime().name
                        showAddSheet = true
                    } else {
                        showCustomFoodDialog = true
                    }
                },
                icon = { Text("＋", fontSize = 20.sp) },
                text = { Text(if (selectedTab == 0) "Agregar" else "Nuevo") },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> HomeScreen(
                    date = viewModel.selectedDate,
                    goal = viewModel.goal,
                    entries = viewModel.entries,
                    canGoNext = viewModel.canGoNext,
                    onPreviousDay = viewModel::goToPreviousDay,
                    onNextDay = viewModel::goToNextDay,
                    onToday = viewModel::goToToday,
                    onEditGoal = { showGoalDialog = true },
                    onAddToMeal = { meal ->
                        addSheetMealName = meal.name
                        showAddSheet = true
                    },
                    onDeleteEntry = { entry ->
                        viewModel.deleteEntry(entry)
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = "«${entry.name}» eliminado",
                                actionLabel = "Deshacer",
                                withDismissAction = true,
                                duration = SnackbarDuration.Long
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                viewModel.restoreEntry(entry)
                            }
                        }
                    }
                )

                else -> CatalogScreen(
                    foods = viewModel.allFoods,
                    customIds = viewModel.customFoods.map { it.id }.toSet(),
                    onAdd = { food, grams, meal ->
                        viewModel.addEntry(food, grams, meal)
                        showMessage("${food.name} agregado a ${meal.label}")
                    },
                    onCreateCustom = { showCustomFoodDialog = true },
                    onDeleteCustom = { food ->
                        viewModel.deleteCustomFood(food)
                        showMessage("${food.name} eliminado del catálogo")
                    }
                )
            }
        }
    }

    if (showAddSheet) {
        AddFoodSheet(
            foods = viewModel.allFoods,
            initialMeal = Meal.fromName(addSheetMealName),
            onDismiss = { showAddSheet = false },
            onAdd = { food, grams, meal ->
                viewModel.addEntry(food, grams, meal)
                showAddSheet = false
                showMessage("${food.name} agregado a ${meal.label}")
            },
            onCreateCustom = {
                showAddSheet = false
                showCustomFoodDialog = true
            }
        )
    }

    if (showGoalDialog) {
        GoalDialog(
            goal = viewModel.goal,
            onDismiss = { showGoalDialog = false },
            onSave = { newGoal ->
                viewModel.updateGoal(newGoal)
                showGoalDialog = false
                showMessage("Meta diaria actualizada")
            }
        )
    }

    if (showCustomFoodDialog) {
        CustomFoodDialog(
            onDismiss = { showCustomFoodDialog = false },
            onSave = { food ->
                viewModel.addCustomFood(food)
                showCustomFoodDialog = false
                showMessage("${food.name} agregado al catálogo")
            }
        )
    }
}
