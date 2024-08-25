package com.example.myrecipe

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenName.CATEGORIES_SCREEN) {
        composable(ScreenName.CATEGORIES_SCREEN) {
            RecipeScreen(
                onCategoryClick = {
                    navController.navigate(ScreenName.MEALS_SCREEN + "/$it")
                }
            )
        }
        composable(
            ScreenName.MEALS_SCREEN + "/{category}",
            arguments = listOf(navArgument("category") {
                type = NavType.StringType
            })
        ) {
            val category = it.arguments?.getString("category")
            MealScreen(category, onMealClick = {
                navController.navigate(ScreenName.MEAL_DETAIL_SCREEN + "/$it")
            })
        }
        composable(
            ScreenName.MEAL_DETAIL_SCREEN + "/{idMeal}",
            arguments = listOf(navArgument("idMeal") {
                type = NavType.StringType
            })
        ) {
            val idMeal = it.arguments?.getString("idMeal")
            MealDetailScreen(idMeal!!)
        }
    }
}
