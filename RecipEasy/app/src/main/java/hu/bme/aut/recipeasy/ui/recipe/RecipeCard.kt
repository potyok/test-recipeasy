package hu.bme.aut.recipeasy.ui.recipe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.recipeasy.R
import hu.bme.aut.recipeasy.domain.model.recipe.Recipe
import hu.bme.aut.recipeasy.ui.theme.appSecondaryColor
import hu.bme.aut.recipeasy.ui.theme.onAppPrimaryColor
import hu.bme.aut.recipeasy.ui.theme.onAppSecondaryColor

@Composable
fun Recipe.ToRecipeCard(isFavorite: Boolean = false, onClick: (Recipe) -> Unit = {}) {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = appSecondaryColor,
            contentColor = onAppSecondaryColor
        ),
        modifier = Modifier.fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(this) }
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = Icons.Default.Image.name,
                modifier = Modifier.height(120.dp).width(120.dp)
            )
            Box {
                Column {
                    Text(
                        text = strMeal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 2,
                        modifier = Modifier.fillMaxWidth()
                            .padding(end = 50.dp, top = 10.dp),
                        overflow = TextOverflow.Ellipsis
                    )

                    Row {
                        Text(
                            text = stringResource(R.string.category),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = strCategory,
                            fontSize = 14.sp
                        )
                    }

                    Row {
                        Text(
                            text = stringResource(R.string.area),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = strArea,
                            fontSize = 14.sp
                        )
                    }
                }
                if (isFavorite) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = Icons.Default.Bookmark.name,
                        modifier = Modifier.align(Alignment.TopEnd)
                            .padding(end = 10.dp, top = 10.dp)
                    )
                }
            }
        }
    }
}