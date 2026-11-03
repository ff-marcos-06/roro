package com.example.rodriguez.data

/**
 * Catálogo base de alimentos. Las calorías y macros están expresados por 100 g.
 * Cada alimento incluye una porción habitual para mostrar sus calorías por porción.
 */
object FoodCatalog {

    val foods: List<Food> = listOf(
        // Frutas
        Food(name = "Manzana", emoji = "🍎", category = FoodCategory.FRUTAS, kcalPer100g = 52, proteinPer100g = 0.3, carbsPer100g = 13.8, fatPer100g = 0.2, portionGrams = 180),
        Food(name = "Plátano", emoji = "🍌", category = FoodCategory.FRUTAS, kcalPer100g = 89, proteinPer100g = 1.1, carbsPer100g = 22.8, fatPer100g = 0.3, portionGrams = 120),
        Food(name = "Naranja", emoji = "🍊", category = FoodCategory.FRUTAS, kcalPer100g = 47, proteinPer100g = 0.9, carbsPer100g = 11.8, fatPer100g = 0.1, portionGrams = 150),
        Food(name = "Fresa", emoji = "🍓", category = FoodCategory.FRUTAS, kcalPer100g = 32, proteinPer100g = 0.7, carbsPer100g = 7.7, fatPer100g = 0.3, portionGrams = 150),
        Food(name = "Uvas", emoji = "🍇", category = FoodCategory.FRUTAS, kcalPer100g = 69, proteinPer100g = 0.7, carbsPer100g = 18.1, fatPer100g = 0.2, portionGrams = 100),
        Food(name = "Sandía", emoji = "🍉", category = FoodCategory.FRUTAS, kcalPer100g = 30, proteinPer100g = 0.6, carbsPer100g = 7.6, fatPer100g = 0.2, portionGrams = 250),
        Food(name = "Piña", emoji = "🍍", category = FoodCategory.FRUTAS, kcalPer100g = 50, proteinPer100g = 0.5, carbsPer100g = 13.1, fatPer100g = 0.1, portionGrams = 150),
        Food(name = "Aguacate", emoji = "🥑", category = FoodCategory.FRUTAS, kcalPer100g = 160, proteinPer100g = 2.0, carbsPer100g = 8.5, fatPer100g = 14.7, portionGrams = 100),
        Food(name = "Mango", emoji = "🥭", category = FoodCategory.FRUTAS, kcalPer100g = 60, proteinPer100g = 0.8, carbsPer100g = 15.0, fatPer100g = 0.4, portionGrams = 150),
        Food(name = "Pera", emoji = "🍐", category = FoodCategory.FRUTAS, kcalPer100g = 57, proteinPer100g = 0.4, carbsPer100g = 15.2, fatPer100g = 0.1, portionGrams = 170),
        Food(name = "Kiwi", emoji = "🥝", category = FoodCategory.FRUTAS, kcalPer100g = 61, proteinPer100g = 1.1, carbsPer100g = 14.7, fatPer100g = 0.5, portionGrams = 100),
        Food(name = "Limón", emoji = "🍋", category = FoodCategory.FRUTAS, kcalPer100g = 29, proteinPer100g = 1.1, carbsPer100g = 9.3, fatPer100g = 0.3, portionGrams = 50),

        // Verduras
        Food(name = "Brócoli", emoji = "🥦", category = FoodCategory.VERDURAS, kcalPer100g = 34, proteinPer100g = 2.8, carbsPer100g = 6.6, fatPer100g = 0.4, portionGrams = 100),
        Food(name = "Zanahoria", emoji = "🥕", category = FoodCategory.VERDURAS, kcalPer100g = 41, proteinPer100g = 0.9, carbsPer100g = 9.6, fatPer100g = 0.2, portionGrams = 80),
        Food(name = "Tomate", emoji = "🍅", category = FoodCategory.VERDURAS, kcalPer100g = 18, proteinPer100g = 0.9, carbsPer100g = 3.9, fatPer100g = 0.2, portionGrams = 100),
        Food(name = "Lechuga", emoji = "🥬", category = FoodCategory.VERDURAS, kcalPer100g = 15, proteinPer100g = 1.4, carbsPer100g = 2.9, fatPer100g = 0.2, portionGrams = 80),
        Food(name = "Papa cocida", emoji = "🥔", category = FoodCategory.VERDURAS, kcalPer100g = 87, proteinPer100g = 1.9, carbsPer100g = 20.1, fatPer100g = 0.1, portionGrams = 150),
        Food(name = "Camote", emoji = "🍠", category = FoodCategory.VERDURAS, kcalPer100g = 86, proteinPer100g = 1.6, carbsPer100g = 20.1, fatPer100g = 0.1, portionGrams = 150),
        Food(name = "Pepino", emoji = "🥒", category = FoodCategory.VERDURAS, kcalPer100g = 15, proteinPer100g = 0.7, carbsPer100g = 3.6, fatPer100g = 0.1, portionGrams = 100),
        Food(name = "Espinaca", emoji = "🍃", category = FoodCategory.VERDURAS, kcalPer100g = 23, proteinPer100g = 2.9, carbsPer100g = 3.6, fatPer100g = 0.4, portionGrams = 100),
        Food(name = "Cebolla", emoji = "🧅", category = FoodCategory.VERDURAS, kcalPer100g = 40, proteinPer100g = 1.1, carbsPer100g = 9.3, fatPer100g = 0.1, portionGrams = 60),
        Food(name = "Pimiento", emoji = "🫑", category = FoodCategory.VERDURAS, kcalPer100g = 31, proteinPer100g = 1.0, carbsPer100g = 6.0, fatPer100g = 0.3, portionGrams = 100),
        Food(name = "Elote", emoji = "🌽", category = FoodCategory.VERDURAS, kcalPer100g = 86, proteinPer100g = 3.3, carbsPer100g = 18.7, fatPer100g = 1.4, portionGrams = 100),

        // Cereales
        Food(name = "Arroz blanco cocido", emoji = "🍚", category = FoodCategory.CEREALES, kcalPer100g = 130, proteinPer100g = 2.7, carbsPer100g = 28.2, fatPer100g = 0.3, portionGrams = 150),
        Food(name = "Pasta cocida", emoji = "🍝", category = FoodCategory.CEREALES, kcalPer100g = 158, proteinPer100g = 5.8, carbsPer100g = 30.9, fatPer100g = 0.9, portionGrams = 150),
        Food(name = "Pan blanco", emoji = "🍞", category = FoodCategory.CEREALES, kcalPer100g = 265, proteinPer100g = 9.0, carbsPer100g = 49.0, fatPer100g = 3.2, portionGrams = 30),
        Food(name = "Pan integral", emoji = "🍞", category = FoodCategory.CEREALES, kcalPer100g = 247, proteinPer100g = 13.0, carbsPer100g = 41.0, fatPer100g = 3.4, portionGrams = 30),
        Food(name = "Avena", emoji = "🥣", category = FoodCategory.CEREALES, kcalPer100g = 389, proteinPer100g = 16.9, carbsPer100g = 66.3, fatPer100g = 6.9, portionGrams = 40),
        Food(name = "Tortilla de maíz", emoji = "🌮", category = FoodCategory.CEREALES, kcalPer100g = 218, proteinPer100g = 5.7, carbsPer100g = 45.0, fatPer100g = 2.9, portionGrams = 30),
        Food(name = "Quinoa cocida", emoji = "🍚", category = FoodCategory.CEREALES, kcalPer100g = 120, proteinPer100g = 4.4, carbsPer100g = 21.3, fatPer100g = 1.9, portionGrams = 150),
        Food(name = "Tortilla de trigo", emoji = "🫓", category = FoodCategory.CEREALES, kcalPer100g = 306, proteinPer100g = 8.0, carbsPer100g = 51.0, fatPer100g = 7.0, portionGrams = 40),
        Food(name = "Cereal de desayuno", emoji = "🥣", category = FoodCategory.CEREALES, kcalPer100g = 350, proteinPer100g = 7.0, carbsPer100g = 80.0, fatPer100g = 2.0, portionGrams = 40),
        Food(name = "Arepa", emoji = "🫓", category = FoodCategory.CEREALES, kcalPer100g = 219, proteinPer100g = 5.0, carbsPer100g = 37.0, fatPer100g = 5.0, portionGrams = 100),

        // Proteínas
        Food(name = "Pechuga de pollo", emoji = "🍗", category = FoodCategory.PROTEINAS, kcalPer100g = 165, proteinPer100g = 31.0, carbsPer100g = 0.0, fatPer100g = 3.6, portionGrams = 150),
        Food(name = "Carne de res", emoji = "🥩", category = FoodCategory.PROTEINAS, kcalPer100g = 250, proteinPer100g = 26.0, carbsPer100g = 0.0, fatPer100g = 15.0, portionGrams = 120),
        Food(name = "Cerdo", emoji = "🍖", category = FoodCategory.PROTEINAS, kcalPer100g = 242, proteinPer100g = 27.0, carbsPer100g = 0.0, fatPer100g = 14.0, portionGrams = 120),
        Food(name = "Salmón", emoji = "🐟", category = FoodCategory.PROTEINAS, kcalPer100g = 208, proteinPer100g = 20.0, carbsPer100g = 0.0, fatPer100g = 13.0, portionGrams = 120),
        Food(name = "Atún en agua", emoji = "🐟", category = FoodCategory.PROTEINAS, kcalPer100g = 116, proteinPer100g = 25.5, carbsPer100g = 0.0, fatPer100g = 0.8, portionGrams = 100),
        Food(name = "Huevo", emoji = "🥚", category = FoodCategory.PROTEINAS, kcalPer100g = 155, proteinPer100g = 13.0, carbsPer100g = 1.1, fatPer100g = 11.0, portionGrams = 50),
        Food(name = "Frijoles cocidos", emoji = "🫘", category = FoodCategory.PROTEINAS, kcalPer100g = 127, proteinPer100g = 8.7, carbsPer100g = 22.8, fatPer100g = 0.5, portionGrams = 150),
        Food(name = "Lentejas cocidas", emoji = "🫘", category = FoodCategory.PROTEINAS, kcalPer100g = 116, proteinPer100g = 9.0, carbsPer100g = 20.1, fatPer100g = 0.4, portionGrams = 150),
        Food(name = "Tofu", emoji = "🧊", category = FoodCategory.PROTEINAS, kcalPer100g = 76, proteinPer100g = 8.0, carbsPer100g = 1.9, fatPer100g = 4.8, portionGrams = 100),
        Food(name = "Camarones", emoji = "🍤", category = FoodCategory.PROTEINAS, kcalPer100g = 99, proteinPer100g = 24.0, carbsPer100g = 0.2, fatPer100g = 0.3, portionGrams = 100),
        Food(name = "Jamón de pavo", emoji = "🥓", category = FoodCategory.PROTEINAS, kcalPer100g = 104, proteinPer100g = 17.0, carbsPer100g = 3.0, fatPer100g = 2.0, portionGrams = 50),
        Food(name = "Garbanzos cocidos", emoji = "🫘", category = FoodCategory.PROTEINAS, kcalPer100g = 164, proteinPer100g = 8.9, carbsPer100g = 27.4, fatPer100g = 2.6, portionGrams = 150),

        // Lácteos
        Food(name = "Leche entera", emoji = "🥛", category = FoodCategory.LACTEOS, kcalPer100g = 61, proteinPer100g = 3.2, carbsPer100g = 4.8, fatPer100g = 3.3, portionGrams = 240),
        Food(name = "Leche descremada", emoji = "🥛", category = FoodCategory.LACTEOS, kcalPer100g = 34, proteinPer100g = 3.4, carbsPer100g = 5.0, fatPer100g = 0.1, portionGrams = 240),
        Food(name = "Yogur natural", emoji = "🥣", category = FoodCategory.LACTEOS, kcalPer100g = 61, proteinPer100g = 3.5, carbsPer100g = 4.7, fatPer100g = 3.3, portionGrams = 150),
        Food(name = "Yogur griego", emoji = "🥣", category = FoodCategory.LACTEOS, kcalPer100g = 97, proteinPer100g = 9.0, carbsPer100g = 3.9, fatPer100g = 5.0, portionGrams = 150),
        Food(name = "Queso fresco", emoji = "🧀", category = FoodCategory.LACTEOS, kcalPer100g = 264, proteinPer100g = 17.0, carbsPer100g = 4.0, fatPer100g = 20.0, portionGrams = 30),
        Food(name = "Queso cheddar", emoji = "🧀", category = FoodCategory.LACTEOS, kcalPer100g = 403, proteinPer100g = 25.0, carbsPer100g = 1.3, fatPer100g = 33.0, portionGrams = 30),
        Food(name = "Mantequilla", emoji = "🧈", category = FoodCategory.LACTEOS, kcalPer100g = 717, proteinPer100g = 0.9, carbsPer100g = 0.1, fatPer100g = 81.0, portionGrams = 10),
        Food(name = "Requesón", emoji = "🥣", category = FoodCategory.LACTEOS, kcalPer100g = 98, proteinPer100g = 11.1, carbsPer100g = 3.4, fatPer100g = 4.3, portionGrams = 100),

        // Bebidas
        Food(name = "Café con leche", emoji = "☕", category = FoodCategory.BEBIDAS, kcalPer100g = 42, proteinPer100g = 2.0, carbsPer100g = 4.0, fatPer100g = 1.5, portionGrams = 240),
        Food(name = "Jugo de naranja", emoji = "🧃", category = FoodCategory.BEBIDAS, kcalPer100g = 45, proteinPer100g = 0.7, carbsPer100g = 10.4, fatPer100g = 0.2, portionGrams = 240),
        Food(name = "Refresco de cola", emoji = "🥤", category = FoodCategory.BEBIDAS, kcalPer100g = 42, proteinPer100g = 0.0, carbsPer100g = 10.6, fatPer100g = 0.0, portionGrams = 355),
        Food(name = "Cerveza", emoji = "🍺", category = FoodCategory.BEBIDAS, kcalPer100g = 43, proteinPer100g = 0.5, carbsPer100g = 3.6, fatPer100g = 0.0, portionGrams = 355),
        Food(name = "Agua", emoji = "💧", category = FoodCategory.BEBIDAS, kcalPer100g = 0, proteinPer100g = 0.0, carbsPer100g = 0.0, fatPer100g = 0.0, portionGrams = 250),
        Food(name = "Batido de proteína", emoji = "🥤", category = FoodCategory.BEBIDAS, kcalPer100g = 120, proteinPer100g = 24.0, carbsPer100g = 5.0, fatPer100g = 1.0, portionGrams = 300),
        Food(name = "Té sin azúcar", emoji = "🍵", category = FoodCategory.BEBIDAS, kcalPer100g = 1, proteinPer100g = 0.0, carbsPer100g = 0.2, fatPer100g = 0.0, portionGrams = 240),
        Food(name = "Chocolate caliente", emoji = "☕", category = FoodCategory.BEBIDAS, kcalPer100g = 77, proteinPer100g = 3.5, carbsPer100g = 10.0, fatPer100g = 2.5, portionGrams = 240),

        // Snacks
        Food(name = "Almendras", emoji = "🌰", category = FoodCategory.SNACKS, kcalPer100g = 579, proteinPer100g = 21.2, carbsPer100g = 21.6, fatPer100g = 49.9, portionGrams = 30),
        Food(name = "Nueces", emoji = "🌰", category = FoodCategory.SNACKS, kcalPer100g = 654, proteinPer100g = 15.2, carbsPer100g = 13.7, fatPer100g = 65.2, portionGrams = 30),
        Food(name = "Maní", emoji = "🥜", category = FoodCategory.SNACKS, kcalPer100g = 567, proteinPer100g = 25.8, carbsPer100g = 16.1, fatPer100g = 49.2, portionGrams = 30),
        Food(name = "Chocolate", emoji = "🍫", category = FoodCategory.SNACKS, kcalPer100g = 546, proteinPer100g = 4.9, carbsPer100g = 61.2, fatPer100g = 31.3, portionGrams = 30),
        Food(name = "Galletas de chocolate", emoji = "🍪", category = FoodCategory.SNACKS, kcalPer100g = 480, proteinPer100g = 5.6, carbsPer100g = 63.0, fatPer100g = 22.0, portionGrams = 30),
        Food(name = "Papas fritas", emoji = "🍟", category = FoodCategory.SNACKS, kcalPer100g = 536, proteinPer100g = 7.0, carbsPer100g = 53.0, fatPer100g = 34.0, portionGrams = 30),
        Food(name = "Helado", emoji = "🍦", category = FoodCategory.SNACKS, kcalPer100g = 207, proteinPer100g = 3.5, carbsPer100g = 23.6, fatPer100g = 11.0, portionGrams = 100),
        Food(name = "Palomitas", emoji = "🍿", category = FoodCategory.SNACKS, kcalPer100g = 387, proteinPer100g = 12.9, carbsPer100g = 77.8, fatPer100g = 4.5, portionGrams = 30),
        Food(name = "Galleta de avena", emoji = "🍪", category = FoodCategory.SNACKS, kcalPer100g = 450, proteinPer100g = 6.0, carbsPer100g = 68.0, fatPer100g = 17.0, portionGrams = 30),

        // Comidas preparadas
        Food(name = "Pizza", emoji = "🍕", category = FoodCategory.COMIDAS, kcalPer100g = 266, proteinPer100g = 11.0, carbsPer100g = 33.0, fatPer100g = 10.0, portionGrams = 100),
        Food(name = "Hamburguesa", emoji = "🍔", category = FoodCategory.COMIDAS, kcalPer100g = 295, proteinPer100g = 17.0, carbsPer100g = 24.0, fatPer100g = 15.0, portionGrams = 150),
        Food(name = "Tacos", emoji = "🌮", category = FoodCategory.COMIDAS, kcalPer100g = 226, proteinPer100g = 9.0, carbsPer100g = 20.0, fatPer100g = 12.0, portionGrams = 100),
        Food(name = "Sushi", emoji = "🍣", category = FoodCategory.COMIDAS, kcalPer100g = 150, proteinPer100g = 6.0, carbsPer100g = 30.0, fatPer100g = 1.0, portionGrams = 100),
        Food(name = "Ensalada César", emoji = "🥗", category = FoodCategory.COMIDAS, kcalPer100g = 190, proteinPer100g = 7.0, carbsPer100g = 8.0, fatPer100g = 15.0, portionGrams = 200),
        Food(name = "Sopa de pollo", emoji = "🍲", category = FoodCategory.COMIDAS, kcalPer100g = 38, proteinPer100g = 3.0, carbsPer100g = 4.5, fatPer100g = 0.8, portionGrams = 300),
        Food(name = "Sándwich de pavo", emoji = "🥪", category = FoodCategory.COMIDAS, kcalPer100g = 230, proteinPer100g = 15.0, carbsPer100g = 28.0, fatPer100g = 6.0, portionGrams = 150),
        Food(name = "Burrito", emoji = "🌯", category = FoodCategory.COMIDAS, kcalPer100g = 206, proteinPer100g = 8.0, carbsPer100g = 27.0, fatPer100g = 7.0, portionGrams = 200),
        Food(name = "Ramen", emoji = "🍜", category = FoodCategory.COMIDAS, kcalPer100g = 436, proteinPer100g = 18.0, carbsPer100g = 63.0, fatPer100g = 12.0, portionGrams = 300),
        Food(name = "Ceviche", emoji = "🐟", category = FoodCategory.COMIDAS, kcalPer100g = 120, proteinPer100g = 18.0, carbsPer100g = 6.0, fatPer100g = 2.0, portionGrams = 200),
        Food(name = "Paella", emoji = "🥘", category = FoodCategory.COMIDAS, kcalPer100g = 156, proteinPer100g = 7.0, carbsPer100g = 24.0, fatPer100g = 4.0, portionGrams = 250),

        // Grasas y aderezos
        Food(name = "Aceite de oliva", emoji = "🫒", category = FoodCategory.GRASAS, kcalPer100g = 884, proteinPer100g = 0.0, carbsPer100g = 0.0, fatPer100g = 100.0, portionGrams = 10),
        Food(name = "Mayonesa", emoji = "🥚", category = FoodCategory.GRASAS, kcalPer100g = 680, proteinPer100g = 1.0, carbsPer100g = 1.0, fatPer100g = 75.0, portionGrams = 15),
        Food(name = "Mantequilla de maní", emoji = "🥜", category = FoodCategory.GRASAS, kcalPer100g = 588, proteinPer100g = 25.0, carbsPer100g = 20.0, fatPer100g = 50.0, portionGrams = 20),
        Food(name = "Aderezo ranchero", emoji = "🥣", category = FoodCategory.GRASAS, kcalPer100g = 450, proteinPer100g = 1.0, carbsPer100g = 6.0, fatPer100g = 47.0, portionGrams = 30)
    )
}
