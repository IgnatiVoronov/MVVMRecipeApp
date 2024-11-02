package com.example.mvvmrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvmrecipeapp.presentation.BaseApplication
import com.example.mvvmrecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.mvvmrecipeapp.presentation.components.DefaultSnackbar
import com.example.mvvmrecipeapp.presentation.components.RecipeView
import com.example.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.example.mvvmrecipeapp.presentation.ui.recipe.RecipeEvent.GetRecipeEvent
import com.example.mvvmrecipeapp.ui.theme.MVVMRecipeAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private var snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { recipeId ->
            viewModel.onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                val scaffoldState = rememberScaffoldState()

                val snackbarHostState = remember { SnackbarHostState() }

                MVVMRecipeAppTheme(darkTheme = application.isDark.value) {
                    Scaffold(
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding)
                        ) {
                            if (loading && recipe == null) {
                                Text("Loading...")
                            } else {
                                recipe?.let {
                                    if (it.id == 1) {//snackbar demonstration
                                        snackbarController.showSnackbar(
                                            message = "An error occurred with this recipe",
                                            actionLabel = "Ok",
                                            snackbarHostState = snackbarHostState
                                        )
                                    } else {
                                        RecipeView(recipe = it)
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
                }

            }
        }
    }
}