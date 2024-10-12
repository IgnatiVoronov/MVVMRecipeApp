package com.example.mvvmrecipeapp.presentation.ui.recipe_list

sealed class RecipeListEvent {

    data object NewSearchEvent : RecipeListEvent()

    data object NextPageEvent : RecipeListEvent()

    //restore after process death
    data object RestoreStateEvent : RecipeListEvent()
}