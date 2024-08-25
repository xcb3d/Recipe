package com.example.myrecipe

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeService = retrofit.create(CategoryApiService::class.java)
val mealService = retrofit.create(MealApiService::class.java)
val mealDetailService = retrofit.create(MealDetailApiService::class.java)

interface CategoryApiService {
    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}

//Write an interface for MealApiService with parameter category and return MealResponse
interface MealApiService {
    @GET("filter.php")
    suspend fun getMeals(@Query("c") category: String): MealResponse
}

//Write an interface for MealDetailApiService with parameter id and return MealDetailResponse
interface MealDetailApiService {
    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") id: String): MealDetailResponse
}

