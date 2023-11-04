package com.example.rickandmorty.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.app.utils.extensions.clickableWithRipple

@Composable
fun TopAppBarRow(
    title: String,
    icon1: Int? = null,
    icon1ContentDescription: String = "",
    icon2: Int? = null,
    icon2ContentDescription: String = "",
    onIcon1Click: (() -> Unit)? = null,
    onIcon2Click: (() -> Unit)? = null
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            modifier = Modifier.weight(1f, true),
        )
        if (icon2 != null) {
            Icon(
                painter = painterResource(
                    id = icon2
                ),
                contentDescription = icon2ContentDescription,
                modifier = Modifier
                    .padding(16.dp)
                    .clickableWithRipple {
                        onIcon2Click?.invoke()
                    }
            )
        }
        if (icon1 != null) {
            Icon(
                painter = painterResource(
                    id = icon1
                ),
                contentDescription = icon1ContentDescription,
                modifier = Modifier
                    .padding(16.dp)
                    .clickableWithRipple {
                        onIcon1Click?.invoke()
                    }
            )
        }

    }
}
