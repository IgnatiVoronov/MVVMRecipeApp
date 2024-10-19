package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.mvvmrecipeapp.presentation.BaseApplication
import com.example.mvvmrecipeapp.presentation.components.RecipeList
import com.example.mvvmrecipeapp.presentation.components.SearchAppBar
import com.example.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.mvvmrecipeapp.ui.theme.MVVMRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                MVVMRecipeAppTheme(darkTheme = application.isDark.value) {

                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value
                    val categoryScrollPosition = viewModel.categoryScrollPosition

                    val loading = viewModel.loading.value

                    val page = viewModel.page.value

                    val snackbarHostState = remember { SnackbarHostState() }

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.getScope().launch {
                                            snackbarController
                                                .showSnackbar(
                                                    snackbarHostState = snackbarHostState,
                                                    message = "Invalid category: Milk!",
                                                    actionLabel = "Hide"
                                                )
                                        }
                                    } else {
                                        viewModel.onTriggerEvent(NewSearchEvent)
                                    }
                                },
                                categories = getAllFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                scrollPosition = categoryScrollPosition,
                                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = application::toggleLightTheme
                            )
                        }
                    ) { innerPadding ->
                        RecipeList(
                            innerPadding = innerPadding,
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onNextPage = {
                                viewModel.onTriggerEvent(NextPageEvent)
                            },
                            snackbarHostState = snackbarHostState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun GradientDemo() {
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Blue
    )
    val brush = Brush.linearGradient(
        colors,
        start = Offset(200f, 200f),
        end = Offset(400f, 400f)
    )
    Surface(shape = MaterialTheme.shapes.small) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }
}