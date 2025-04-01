package hu.bme.aut.recipeasy.feature.category.list_category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.recipeasy.R
import hu.bme.aut.recipeasy.domain.model.category.Category
import hu.bme.aut.recipeasy.ui.category.ToCategoryCard
import hu.bme.aut.recipeasy.ui.theme.onAppBackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCategoryScreen(
    viewModel: ListCategoryViewModel = hiltViewModel(),
    onCategoryClick: (Category) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ListCategoryScreenEvent.RefreshCategories)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = { viewModel.onEvent(ListCategoryScreenEvent.RefreshCategories) },
    ) {
        if (state.categories.isEmpty()) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.no_categories_available),
                    color = onAppBackgroundColor
                )
            }
        }
        LazyColumn (modifier = Modifier.fillMaxWidth().padding(10.dp)) {
            items(state.categories) {
                it.ToCategoryCard(onClick = onCategoryClick)
            }
        }
    }
}