package de.greiwies.rainbow_organizor.utilities

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import de.greiwies.rainbow_organizor.ui.theme.GrayHalfTransparent

@Composable
fun GrayscalableOverlayWithContent(expandFab : MutableState<Boolean>, content: @Composable () -> Unit)
{
    // Creates a box which closes on click without animation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (expandFab.value) GrayHalfTransparent else Color.Transparent)
            .clickable (
                indication = null, //suppresses the Ripple-Animation
                interactionSource = remember { MutableInteractionSource() }
            ){ expandFab.value = false }
            .zIndex(1F)
    ){
        content()
    }
}