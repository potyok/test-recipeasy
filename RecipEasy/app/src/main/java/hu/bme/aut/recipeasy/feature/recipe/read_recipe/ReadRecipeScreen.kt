package hu.bme.aut.recipeasy.feature.recipe.read_recipe

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import hu.bme.aut.recipeasy.R
import hu.bme.aut.recipeasy.domain.model.recipe.tags
import hu.bme.aut.recipeasy.domain.model.recipe.videoId
import hu.bme.aut.recipeasy.ui.theme.appSecondaryColor
import hu.bme.aut.recipeasy.ui.theme.onAppBackgroundColor
import hu.bme.aut.recipeasy.ui.theme.onAppSecondaryColor
import hu.bme.aut.recipeasy.ui.theme.onSurfaceDark
import hu.bme.aut.recipeasy.ui.theme.onTopAppBarColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReadRecipeScreen(
    recipeId: String,
    viewModel: ReadRecipeViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ReadRecipeEvent.LoadRecipe(recipeId))
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
        state.recipe?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = it.strMeal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = onAppBackgroundColor
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Text(
                        text = stringResource(R.string.category),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = onAppBackgroundColor
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = it.strCategory,
                        fontSize = 16.sp,
                        color = onAppBackgroundColor
                    )
                }

                Row {
                    Text(
                        text = stringResource(R.string.area),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = onAppBackgroundColor
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = it.strArea,
                        fontSize = 16.sp,
                        color = onAppBackgroundColor
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow (
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    it.tags().forEach {
                        RecipeTag(name = it)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.ingredients),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = onAppBackgroundColor
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = stringResource(R.string.instructions),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = onAppBackgroundColor
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = it.strInstructions,
                    fontSize = 16.sp,
                    color = onAppBackgroundColor
                )
                Spacer(modifier = Modifier.height(10.dp))
                VideoInstruction(
                    videoId = it.videoId(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))
            }

        }
    }
}

@Composable
fun VideoInstruction(
    videoId: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = {
            YouTubePlayerView(it).apply {
                addYouTubePlayerListener(
                    object : AbstractYouTubePlayerListener() {
                        override fun onReady(
                            youTubePlayer:
                            YouTubePlayer
                        ) {
                            super.onReady(youTubePlayer)
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun RecipeTag(name: String) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = appSecondaryColor,
            contentColor = onAppSecondaryColor
        )
    ) {
        Text(
            text = name,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 30.dp)
        )
    }
}