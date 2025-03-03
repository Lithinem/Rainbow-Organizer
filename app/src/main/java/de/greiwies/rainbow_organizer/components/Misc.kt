package de.greiwies.rainbow_organizer.components

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import de.greiwies.rainbow_organizer.R
import de.greiwies.rainbow_organizer.ui.theme.OverlayBackgroundGrayHalfTransparent

@Composable
fun GrayscalableOverlayWithContent(activateOverlay : MutableState<Boolean>, overlayBlocksInteractionBelow: Boolean, overlayClosesOnSelfInteraction: Boolean)
{
    val animatedColor by animateColorAsState(
        targetValue = if (activateOverlay.value) OverlayBackgroundGrayHalfTransparent else Color.Transparent,
        animationSpec = tween(durationMillis = integerResource(id = R.integer.DefaultAnimationDurationMilliseconds))
    )

    // Creates a box which closes on click without click animation
    // is only active when used to prevent blocking everything due to its clickable behaviour
    if (activateOverlay.value)
    {
        if (overlayBlocksInteractionBelow)
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(animatedColor)
                    .clickable (
                        indication = null, //suppresses the Ripple-Animation
                        interactionSource = remember { MutableInteractionSource() }
                    ){
                        if(overlayClosesOnSelfInteraction)
                        {
                            activateOverlay.value = false
                        }
                    }
                    .zIndex(1F)
            ){  }
        }
        else
        {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(animatedColor)
                    .zIndex(1F)
            ){  }
        }

    }

}

@Composable
fun VisibleSeparator(){
    val context = LocalContext.current
    val separatorSize = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.SeparatorSize).dp
    }


    Box(modifier = Modifier
        .background(OverlayBackgroundGrayHalfTransparent)
        .fillMaxWidth()
        .height(separatorSize))
}

@Composable
fun HideSystemUI()
{
    // This Approach may cause visual bugs in the emulator to no longer display content. On real devices this error did not occur.
    // You can see that this is an emulator bug, by minimizing the app. Then, in the small preview of all the minimized apps, the content is still displayed correctly.
    val view = LocalView.current
    val window = (view.context as Activity).window
    val insetsController = WindowInsetsControllerCompat(window, view)
    insetsController.apply {
        hide(WindowInsetsCompat.Type.systemBars())
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}