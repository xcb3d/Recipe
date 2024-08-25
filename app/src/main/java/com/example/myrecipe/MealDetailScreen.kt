package com.example.myrecipe

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

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
        var filterMeal : Map<String,Any> = mapOf()
        MealDetail::class.memberProperties.forEach { member ->
            val value = member.get(meal)
            if (value != null && value != ""){
                filterMeal = mapOf(member.name to value)
                Log.d("filterMeal",filterMeal.toString())
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = rememberAsyncImagePainter(meal.strMealThumb), contentDescription = "",modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f))
            LazyVerticalGrid(GridCells.Fixed(3), modifier = Modifier.fillMaxSize()) {
                items(filterMeal.size){
                    Text(text = filterMeal.keys.elementAt(it).toString() + ": " + filterMeal.values.elementAt(it).toString())
                }
            }
        }
    }
}