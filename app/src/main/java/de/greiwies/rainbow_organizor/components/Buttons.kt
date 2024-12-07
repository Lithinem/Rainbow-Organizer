package de.greiwies.rainbow_organizor.components

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import de.greiwies.rainbow_organizor.R
import de.greiwies.rainbow_organizor.ui.theme.MainColorDark

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
        targetValue = if (expandFab.value) integerResource(R.integer.Fab_FullExpandedSize).dp else integerResource(R.integer.Fab_FullInflatedSize).dp,
        animationSpec = tween(durationMillis = integerResource(id = R.integer.DefaultAnimationDurationMilliseconds))
    )
    val currentSize: Dp = animatedSize

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // the FAB overlay. expanding on expandFab == true
        if (currentSize > integerResource(R.integer.Fab_FullInflatedSize).dp) {
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
                // Inner Buttons
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

        // Main FAB. Will be disabled when expanded FAB-Field (see above) ios active
        AnimatedVisibility(visible = !expandFab.value) {
            FloatingActionButton(
                onClick = { expandFab.value = !expandFab.value },
                containerColor = MainColorDark,
                modifier = Modifier.size(56.dp)
                    .offset(x=(-30).dp, y = (-80).dp)
            ) {
                Icon(
                    Icons.Outlined.Add,
                    contentDescription = "Add New Resource",
                    modifier = Modifier.fillMaxSize()
                )
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