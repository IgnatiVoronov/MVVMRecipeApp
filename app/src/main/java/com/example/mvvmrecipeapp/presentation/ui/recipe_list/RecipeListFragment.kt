package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.mvvmrecipeapp.presentation.BaseApplication
import com.example.mvvmrecipeapp.presentation.components.RecipeList
import com.example.mvvmrecipeapp.presentation.components.SearchAppBar
import com.example.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
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

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value
                val categoryScrollPosition = viewModel.categoryScrollPosition

                val loading = viewModel.loading.value

                val page = viewModel.page.intValue

                val snackbarHostState = remember { SnackbarHostState() }

                MVVMRecipeAppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    snackbarHostState = snackbarHostState) {

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