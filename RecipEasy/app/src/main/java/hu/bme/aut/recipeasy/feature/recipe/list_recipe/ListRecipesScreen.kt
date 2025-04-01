package hu.bme.aut.recipeasy.feature.recipe.list_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.recipeasy.R
import hu.bme.aut.recipeasy.domain.model.recipe.Recipe
import hu.bme.aut.recipeasy.ui.recipe.ToRecipeCard
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import hu.bme.aut.recipeasy.ui.theme.appPrimaryColor
import hu.bme.aut.recipeasy.ui.theme.onAppBackgroundColor
import hu.bme.aut.recipeasy.ui.theme.onAppPrimaryColor
import hu.bme.aut.recipeasy.ui.theme.onAppPrimaryInactiveColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListRecipesScreen(
    viewModel: ListRecipesViewModel = hiltViewModel(),
    onRecipeClick: (Recipe) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ListRecipesEvent.RefreshRecipes)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(R.string.total), stringResource(R.string.favourite))
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state.isLoading,
        state = refreshState,
        onRefresh = { viewModel.onEvent(ListRecipesEvent.RefreshRecipes) },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                colors = SearchBarDefaults.colors(
                    containerColor = appPrimaryColor
                ),
                inputField = {
                    SearchBarDefaults.InputField(
                        colors = SearchBarDefaults.inputFieldColors(
                            unfocusedTextColor = onAppPrimaryColor,
                            cursorColor = onAppPrimaryColor,
                            unfocusedPlaceholderColor = onAppPrimaryColor,
                            unfocusedLeadingIconColor = onAppPrimaryColor,
                            focusedLeadingIconColor = onAppPrimaryColor,
                            focusedTextColor = onAppPrimaryColor,
                            focusedPlaceholderColor = onAppPrimaryColor,
                        ),
                        query = state.query,
                        onQueryChange = { viewModel.onEvent(ListRecipesEvent.UpdateQuery(it)) },
                        onSearch = { viewModel.onEvent(ListRecipesEvent.SearchRecipes) },
                        expanded = false,
                        onExpandedChange = {},
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = Icons.Default.Search.name
                            )
                        },
                        placeholder = {
                            Text(text = stringResource(R.string.search))
                        }
                    )
                },
                expanded = false,
                onExpandedChange = {},
            ) {}
            Spacer(modifier = Modifier.height(10.dp))
            TabRow(
                containerColor = appPrimaryColor,
                contentColor = onAppPrimaryColor,
                selectedTabIndex = tabIndex,
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        selectedContentColor = onAppPrimaryColor,
                        unselectedContentColor = onAppPrimaryInactiveColor
                    )
                }
            }
            when (tabIndex) {
                0 -> RecipesList(state.recipes, onRecipeClick)
                1 -> RecipesList(state.favorites, onRecipeClick)
            }


        }
    }
}

@Composable
fun RecipesList(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit
) {
    if (recipes.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.new_recipe_not_available),
                color = onAppBackgroundColor
            )
        }
    } else {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(recipes) {
                it.ToRecipeCard(onClick = onRecipeClick)
            }
        }
    }
}
