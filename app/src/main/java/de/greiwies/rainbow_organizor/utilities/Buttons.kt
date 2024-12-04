package de.greiwies.rainbow_organizor.utilities

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun VariableFunctionOvalButton(
    buttonText: String,
    buttonCalculationInteger: Int,
    onClickAction: (test: Int) -> Unit
) {
    Button(
        onClick = { onClickAction(buttonCalculationInteger) },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        TextInButton(buttonText)
    }
}
@Composable
fun VariableFunctionOvalButton(
    buttonText: String,
    onClickAction: () -> Unit
) {
    Button(
        onClick = { onClickAction() },
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        TextInButton(buttonText)
    }
}


@Composable
private fun TextInButton(buttonText: String){
    Text(
        text = buttonText,
        textAlign = TextAlign.Center
    )
}


@Preview(showBackground = true)
@Composable
fun VariableRoundButtonPreview() {
    MaterialTheme {
        VariableFunctionOvalButton(
            buttonText = "Preview Button",
            42,
            onClickAction = { println("Preview Button Clicked!") } //Wird nicht ausgeführt, da preview
        )
    }
}