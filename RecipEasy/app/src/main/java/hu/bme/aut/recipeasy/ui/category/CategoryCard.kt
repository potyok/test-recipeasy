package hu.bme.aut.recipeasy.ui.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.recipeasy.domain.model.category.Category
import hu.bme.aut.recipeasy.ui.theme.appSecondaryColor
import hu.bme.aut.recipeasy.ui.theme.onAppSecondaryColor

@Composable
fun Category.ToCategoryCard(onClick: (Category) -> Unit = {}) {
    Card(
        colors = CardDefaults.cardColors(
            contentColor = onAppSecondaryColor,
            containerColor = appSecondaryColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick(this) }
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = Icons.Default.Image.name,
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
            )
            Box (modifier = Modifier.fillMaxSize()) {
                Text(
                    text = strCategory,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 50.dp, top = 20.dp)
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = Icons.Default.ChevronRight.name,
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                        .height(24.dp)
                )
            }
        }
    }
}