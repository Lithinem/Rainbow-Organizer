package de.greiwies.rainbow_organizor.utilities

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import de.greiwies.rainbow_organizor.ui.theme.GrayHalfTransparent

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

@Composable
fun ExpandableFab(expandFab : MutableState<Boolean>) {
    val animatedSize by animateDpAsState(
        targetValue = if (expandFab.value) 300.dp else 56.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Text("MeinTestText", modifier = Modifier.align(Alignment.TopCenter))

        // TODO: Codetestblock entfernen
        // Einfahren bei Klick außerhalb des Viertelkreises
        if (expandFab.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(GrayHalfTransparent)
                    .clickable { expandFab.value = false }
            )
        }

        // Der expandierende Viertelkreis
        if (expandFab.value) {
            Box(
                modifier = Modifier
                    .size(animatedSize)
                    .clip(
                        RoundedCornerShape(
                            topStart = animatedSize * 2, // Rundung für Viertelkreis
                            topEnd = 0.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
                    .background(Color.Gray)
                    .align(Alignment.BottomEnd)
                    .zIndex(2F)
                    .clickable{}
            ) {
                // Zusätzliche Buttons im Viertelkreis
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = { /* TODO: Aktion 1 */ }) {
                        Text("Button 1")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { /* TODO: Aktion 2 */ }) {
                        Text("Button 2")
                    }
                }
            }
        }

        // Der Haupt-FAB (ausblenden bei Expansion)
        AnimatedVisibility(visible = !expandFab.value) {
            FloatingActionButton(
                onClick = { expandFab.value = !expandFab.value },
                containerColor = Color.Blue,
                modifier = Modifier.size(56.dp)
            ) {
                Text("+", color = Color.White)
            }
        }
    }
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