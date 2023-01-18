package com.example.rickandmorty.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeButton(text: String = "Button Title", onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(200.dp)
            .height(64.dp)
            .border(
                border = BorderStroke(
                    width = 3.dp,
                    color = Color.White,
                ),
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
    ) {
        Text(text = text, style = MaterialTheme.typography.titleLarge)
    }
}
