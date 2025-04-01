package hu.bme.aut.recipeasy.feature.category.read_category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import hu.bme.aut.recipeasy.R
import hu.bme.aut.recipeasy.ui.theme.onAppBackgroundColor
import hu.bme.aut.recipeasy.ui.theme.onTopAppBarColor

@Composable
fun ReadCategoryScreen(
    categoryId: String,
    viewModel: ReadCategoryViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ReadCategoryEvent.LoadCategory(categoryId))
    }
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Icon(
            imageVector = Icons.Default.Image,
            contentDescription = Icons.Default.Image.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            tint = onAppBackgroundColor
        )
        HorizontalDivider(thickness = 5.dp, color = onTopAppBarColor)
        state.category?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = it.strCategory,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = onAppBackgroundColor
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = it.strCategoryDescription,
                    fontSize = 16.sp,
                    color = onAppBackgroundColor
                )
            }
        }
    }
}