package com.example.myrecipe

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
fun RecipeScreen(
    onCategoryClick: (category: String) -> Unit
) {
    val recipeViewModel : MailViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState
    Box(modifier = Modifier.fillMaxSize()){
        when{
            viewState.isLoading -> {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
            viewState.error != null -> {
                Text(text = "Error : ${viewState.error}")
            }
            else -> {
                RecipeScreen(categories = viewState.list, onCategoryClick = onCategoryClick)
            }
        }
    }
}

@Composable
fun RecipeScreen(
    categories: List<Category>,
    onCategoryClick: (category: String) -> Unit
) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(categories){
            CategoryItem(it, onCategoryClick)
        }
    }
}

@Composable
fun CategoryItem(category: Category, onCategoryClick: (category: String) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = rememberAsyncImagePainter(category.strCategoryThumb), contentDescription = category.strCategoryDescription,modifier = Modifier.fillMaxSize().aspectRatio(1f).clickable {

            onCategoryClick(category.strCategory)
        })
        Text(text = category.strCategory)
    }
}

