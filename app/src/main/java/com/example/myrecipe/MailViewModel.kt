package com.example.myrecipe

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MailViewModel : ViewModel() {
    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState
    private val _mealsState = mutableStateOf(MealState())
    val mealsState: State<MealState> = _mealsState
    private val _mealDetailState = mutableStateOf(MealDetailState())
    val mealDetailState: State<MealDetailState> = _mealDetailState
    init {
        fetchCategories()
    }
    fun onCategoryClick(category: String) {
        fetchMeals(category)
    }
    fun onMealClick(id: String) {
        fetchMealDetails(id)
    }
    private fun fetchCategories() {
        viewModelScope.launch {
            try{
                val response = recipeService.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    isLoading = false,
                    list = response.categories
                )
            } catch (e: Exception) {
                _categoriesState.value = _categoriesState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    private fun fetchMeals(category: String) {
        viewModelScope.launch {
            try{
                val response = mealService.getMeals(category)
                _mealsState.value = _mealsState.value.copy(
                    isLoading = false,
                    list = response.meals)
            } catch (e: Exception) {
                _mealsState.value = _mealsState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    private fun fetchMealDetails(id: String) {
        viewModelScope.launch {
            try{
                val response = mealDetailService.getMealDetail(id)
                _mealDetailState.value = _mealDetailState.value.copy(
                    isLoading = false,
                    list = response.meals
                )
            } catch (e: Exception) {
                _mealDetailState.value = _mealDetailState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    data class RecipeState(
        val isLoading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
    data class MealState(
        val isLoading: Boolean = true,
        val list: List<Meal> = emptyList(),
        val error: String? = null
    )
    data class MealDetailState(
        val isLoading: Boolean = true,
        val list: List<MealDetail> = emptyList(),
        val error: String? = null
    )
}