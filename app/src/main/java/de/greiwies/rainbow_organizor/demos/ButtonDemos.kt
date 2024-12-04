package de.greiwies.rainbow_organizor.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import de.greiwies.rainbow_organizor.utilities.VariableOvalButton

// -----  DEMO Composables ---------

@Composable
fun DemoVariableOvalButton() {
    val counter = remember { mutableStateOf(1) }
    Column {
        VariableOvalButton(
            buttonText = "Preview Button",
            buttonCalculationInteger = counter.value,
            onClickAction = { number ->
                counter.value += number
                println("Counter updated to: ${counter.value}")
            }
        )
    }
}