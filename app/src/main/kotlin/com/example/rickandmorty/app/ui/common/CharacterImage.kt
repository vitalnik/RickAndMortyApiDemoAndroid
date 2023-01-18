package com.example.rickandmorty.app.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun CharacterImage(imageUrl: String, size: Dp = 100.dp, cornerRadius: Dp = 8.dp) {

    Box(
        modifier = Modifier
            .width(size)
            .height(size)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(cornerRadius)
            ),
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .width(size)
                .height(size)
                .clip(shape = RoundedCornerShape(cornerRadius)),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            loading = {
                Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
        )
    }
}

