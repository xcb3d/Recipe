package com.example.myrecipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
fun MealDetailScreen(
    idMeal: String
) {
    val mealDetailViewModel: MailViewModel = viewModel()
    val viewState by mealDetailViewModel.mealDetailState
    mealDetailViewModel.onMealClick(idMeal)
    Box(modifier = Modifier.fillMaxSize()){
        when{
            viewState.isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error : ${viewState.error}")
            }
            else -> {
                MealDetailScreen(mealDetail = viewState.list)
            }
        }
    }
}



@Composable
fun MealDetailScreen(mealDetail: List<MealDetail>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val meal = mealDetail[0]
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item(){
                Image(painter = rememberAsyncImagePainter(meal.strMealThumb), contentDescription = "",modifier = Modifier
                    .wrapContentSize()
                    .aspectRatio(1f))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = meal.strInstructions)
            }
        }
    }
}