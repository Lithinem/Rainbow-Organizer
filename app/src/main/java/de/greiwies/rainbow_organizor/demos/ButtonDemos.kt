package de.greiwies.rainbow_organizor.demos


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import de.greiwies.rainbow_organizor.utilities.ExpandableFab
import de.greiwies.rainbow_organizor.utilities.VariableFunctionOvalButton
import de.greiwies.rainbow_organizor.utilities.GrayscalableOverlayWithContent

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
    // Defines if overlay is active or dormant
    val expandFab = remember { mutableStateOf(false) }

    GrayscalableOverlayWithContent (expandFab) {
        // Defines Content
        Column  {
            Text("MeinTestText")
            Text("MeinTestText")
            Text("MeinTestText")
        }
        Text("MeinTestText")


        //Defines FAB as the End of Content
        ExpandableFab(expandFab)
    }
}