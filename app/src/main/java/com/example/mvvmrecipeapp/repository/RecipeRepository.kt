package com.example.mvvmrecipeapp.repository

import com.example.mvvmrecipeapp.domain.model.Recipe

interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): List<Recipe>

    suspend fun get(token: String, id: Int): Recipe
}