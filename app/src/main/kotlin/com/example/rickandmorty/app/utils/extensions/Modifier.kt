package com.example.rickandmorty.app.utils.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableWithRipple(
    enabled: Boolean = true,
    bounded: Boolean = false,
    onClick: () -> Unit
) = composed {
    clickable(
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        indication = ripple(bounded = bounded),
        onClick = {
            onClick()
        }
    )
}
