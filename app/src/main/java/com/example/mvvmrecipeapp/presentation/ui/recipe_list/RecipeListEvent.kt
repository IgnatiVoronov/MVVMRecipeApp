package com.example.mvvmrecipeapp.presentation.ui.recipe_list

sealed class RecipeListEvent {
    data object NewSearchEvent : RecipeListEvent()
    data object NextPageEvent : RecipeListEvent()
}