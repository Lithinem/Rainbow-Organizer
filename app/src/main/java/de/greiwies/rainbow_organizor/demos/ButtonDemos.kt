package de.greiwies.rainbow_organizor.demos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import androidx.core.content.res.ColorStateListInflaterCompat
import de.greiwies.rainbow_organizor.utilities.ExpandableFab
import de.greiwies.rainbow_organizor.utilities.VariableFunctionOvalButton
import de.greiwies.rainbow_organizor.ui.theme.ContrastColorDark

// -----  DEMO Composables ---------

@Composable
fun DemoVariableFunctionOvalButton() {
    val counter = remember { mutableStateOf(1) }
    Column {
        VariableFunctionOvalButton(
            buttonText = "Preview Button",
            buttonCalculationInteger = counter.value,
            onClickAction = { number ->
                counter.value += number
                println("Counter updated to: ${counter.value}")
            }
        )
    }
}

@Composable
fun DemoExpandableFab()
{
    val expandFab = remember { mutableStateOf(false) }
    // Einfahren bei Klick außerhalb des Viertelkreises
    //if (expandFab.value) {
    //    Box(
    //        modifier = Modifier
    //            .fillMaxSize()
    //            .background(Color.Transparent)
    //            .clickable { expandFab.value = false }
    //            .zIndex(1F)
    //    )
    //}
    ExpandableFab(expandFab)
}