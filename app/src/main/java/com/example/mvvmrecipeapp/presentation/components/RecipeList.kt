package com.example.mvvmrecipeapp.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.mvvmrecipeapp.R
import com.example.mvvmrecipeapp.domain.model.Recipe
import com.example.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    innerPadding: PaddingValues,
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(innerPadding)
    ) {
        if (loading && recipes.isEmpty()) {
            ShimmerRecipeCardItem()
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                        onNextPage(NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if (recipe.id != 0) {
                                val bundle = Bundle()
                                recipe.id?.let { bundle.putInt("recipeId", it) }
                                navController.navigate(R.id.viewRecipe,bundle)
                            } else {
                                snackbarController.getScope().launch {
                                    snackbarController.showSnackbar(
                                        snackbarHostState = snackbarHostState,
                                        message = "Recipe Error",
                                        actionLabel = "Ok"
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
        DefaultSnackbar(
            snackbarHostState = snackbarHostState,
            onDismiss = { snackbarHostState.currentSnackbarData?.dismiss() },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}