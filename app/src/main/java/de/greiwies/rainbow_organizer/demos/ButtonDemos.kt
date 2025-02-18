package de.greiwies.rainbow_organizer.demos


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import de.greiwies.rainbow_organizer.components.VariableFunctionOvalButton
import de.greiwies.rainbow_organizer.components.MainFabWithGrayscaledBackgroundOverlay

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
    MainFabWithGrayscaledBackgroundOverlay()
}