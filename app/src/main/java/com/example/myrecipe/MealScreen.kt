package com.example.myrecipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun MealScreen(category: String?,onMealClick: (id: String) -> Unit) {
    val mealViewModel : MailViewModel = viewModel()
    val viewState by mealViewModel.mealsState
    mealViewModel.onCategoryClick(category!!)
    Box(modifier = Modifier.fillMaxSize()){
        when{
            viewState.isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error : ${viewState.error}")
            }
            else -> {
                MealScreenD(meals = viewState.list,onMealClick = onMealClick)
            }
        }
    }
}

@Composable
fun MealScreenD(
    meals: List<Meal>,
    onMealClick: (id: String) -> Unit
) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(meals){
            MealItem(it,onMealClick)
        }
    }
}

@Composable
fun MealItem(meal: Meal,onMealClick: (id: String) -> Unit ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = rememberAsyncImagePainter(meal.strMealThumb), contentDescription = "",modifier = Modifier.fillMaxSize().aspectRatio(1f).clickable {
            onMealClick(meal.idMeal)
        })
        Text(text = meal.strMeal)
    }

}
