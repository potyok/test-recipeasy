package hu.bme.aut.recipeasy.feature.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.recipeasy.BuildConfig
import hu.bme.aut.recipeasy.R
import hu.bme.aut.recipeasy.ui.theme.onAppBackgroundColor


@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.Image,
                contentDescription = Icons.Default.Image.name,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(CircleShape),
                tint = onAppBackgroundColor

            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.general),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = onAppBackgroundColor
        )
        Text(
            text = "",
            modifier = Modifier.weight(1f),
            color = onAppBackgroundColor
        )
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.version),
                color = onAppBackgroundColor
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = BuildConfig.APP_VERSION)
        }
    }
}