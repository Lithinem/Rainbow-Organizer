package de.greiwies.rainbow_organizor.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import de.greiwies.rainbow_organizor.utilities.VariableRoundButton

// -----  DEMO Composables ---------

@Composable
fun DemoVariableRoundButton() {
    val counter = remember { mutableStateOf(1) }
    Column {
        VariableRoundButton(
            buttonText = "Preview Button",
            buttonCalculationInteger = counter.value,
            onClickAction = { number ->
                counter.value += number
                println("Counter updated to: ${counter.value}")
            }
        )
    }
}