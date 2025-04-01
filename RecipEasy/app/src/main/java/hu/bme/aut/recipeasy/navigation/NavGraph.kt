package hu.bme.aut.recipeasy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.recipeasy.R
import hu.bme.aut.recipeasy.domain.model.main.MainViewModel
import hu.bme.aut.recipeasy.feature.app.AboutScreen
import hu.bme.aut.recipeasy.feature.category.list_category.ListCategoryScreen
import hu.bme.aut.recipeasy.feature.category.read_category.ReadCategoryScreen
import hu.bme.aut.recipeasy.feature.recipe.list_recipe.ListRecipesScreen
import hu.bme.aut.recipeasy.feature.recipe.read_recipe.ReadRecipeScreen
import hu.bme.aut.recipeasy.feature.recipe.read_recipe.ReadRecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    snackBarHostState: SnackbarHostState,
    mainViewModel: MainViewModel = hiltViewModel(),
    readRecipeViewModel: ReadRecipeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val onFailure: (msg: String) -> Unit = { msg ->
        scope.launch {
            snackBarHostState.showSnackbar(
                message = msg
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = mainViewModel.startDestination,
        modifier = modifier
    ) {
        composable(Screen.ListRecipes.route) {
            mainViewModel.setSelectedMenuItem(Screen.ListRecipes.route)
            mainViewModel.updateTitle(newTitle = stringResource(R.string.app_name), showMenu = true)
            ListRecipesScreen {
                navController.navigate(Screen.ReadRecipe.passId(it.idMeal))
            }
        }
        composable(
            route = Screen.ReadRecipe.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            mainViewModel.updateTitle(
                newTitle = stringResource(R.string.details),
                showBackArrow = true
            )
            mainViewModel.setNavigationLambda { navController.popBackStack() }
            ReadRecipeScreen(
                it.arguments?.getString("id")!!,
                readRecipeViewModel
            )
        }
        composable(Screen.ListCategories.route) {
            mainViewModel.setSelectedMenuItem(Screen.ListCategories.route)
            mainViewModel.updateTitle(
                newTitle = stringResource(R.string.categories),
                showMenu = true
            )
            ListCategoryScreen {
                navController.navigate(Screen.ReadCategory.passId(it.idCategory))
            }
        }
        composable(
            route = Screen.ReadCategory.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            mainViewModel.updateTitle(
                newTitle = stringResource(R.string.category_details),
                showBackArrow = true
            )
            mainViewModel.setNavigationLambda { navController.popBackStack() }
            ReadCategoryScreen(categoryId = it.arguments?.getString("id")!!)
        }
        composable(Screen.About.route) {
            mainViewModel.setSelectedMenuItem(Screen.About.route)
            mainViewModel.updateTitle(newTitle = stringResource(R.string.about), showMenu = true)
            AboutScreen()
        }
    }
}