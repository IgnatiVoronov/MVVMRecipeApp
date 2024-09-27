package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeapp.domain.model.Recipe
import com.example.mvvmrecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var categoryScrollPosition: Int = 0

    val loading = mutableStateOf((false))

    val page = mutableIntStateOf(1)

    private var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    //use case #1
    fun newSearch() {
        viewModelScope.launch {
            loading.value = true

            resetSearchState()

            delay(2000)

            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result
            loading.value = false
        }
    }

    //use case#2
    fun nextPage() {
        viewModelScope.launch {
            //prevent duplicate events due to recompose happening to quickly
            if ((recipeListScrollPosition + 1) >= (page.intValue * PAGE_SIZE)) {
                loading.value = true
                incrementPage()

                //just to show pagination, api is fast
                delay(1000)

                if (page.intValue > 1) {
                    val result = repository.search(
                        token = token,
                        page = page.intValue,
                        query = query.value
                    )
                    appendRecipes(result)
                }
                loading.value = false
            }
        }
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.intValue += 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
    }

    /**
     * Called when a new search is executed
     */
    private fun resetSearchState() {
        recipes.value = listOf()
        page.intValue = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }
}