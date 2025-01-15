package de.greiwies.rainbow_organizor.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import de.greiwies.rainbow_organizor.R

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
fun MainFabWithGrayscaledBackgroundOverlay(){
    // Defines if overlay is visible
    val expandFab = remember { mutableStateOf(false) }

    GrayscalableOverlayWithContent (expandFab, true, true)
    //Defines FAB as the End of Content
    ExpandableFab(expandFab)
}

@Composable
fun ExpandableFab(expandFab : MutableState<Boolean>) {
    val animatedSize by animateDpAsState(
        targetValue = if (expandFab.value) integerResource(R.integer.Fab_FullExpandedSize).dp else integerResource(R.integer.Fab_FullInflatedSize).dp,
        animationSpec = tween(durationMillis = integerResource(id = R.integer.DefaultAnimationDurationMilliseconds))
    )
    val currentSize: Dp = animatedSize
    val errorText = stringResource(R.string.function_out_of_scope_error)
    val context = LocalContext.current


    //Outer fill the whole screen box (used as basis for relational positioning)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .zIndex(2F),
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
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.BottomEnd)
                    .clickable(
                        indication = null, //suppresses the Ripple-Animation
                        interactionSource = remember { MutableInteractionSource() }
                    ) {}
            ) {
                val buttonSize = context.resources.getInteger(R.integer.Fab_ContentButtonSize).dp
                val buttonIconSize = context.resources.getInteger(R.integer.Fab_ContentButtonIconSize).dp
                val separatorSize = context.resources.getInteger(R.integer.SeparatorSize).dp

                // Inner Buttons
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 50.dp, end = 30.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.End
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(buttonSize)
                            .clickable{
                                Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.size(10.dp))
                            Image(
                                painter = painterResource(id = R.drawable.add_book),
                                contentDescription = "Select all Button",
                                modifier = Modifier
                                    .size(buttonIconSize),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.size(separatorSize))
                            Text("Hinzufügen")
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 30.dp, start = 50.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(buttonSize)
                            .clickable{
                                Toast.makeText(context, errorText,Toast.LENGTH_LONG).show()
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.size(10.dp))
                            Image(
                                painter = painterResource(id = R.drawable.barcode_scan),
                                contentDescription = "Select all Button",
                                modifier = Modifier
                                    .size(buttonIconSize),
                                contentScale = ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.size(separatorSize))
                            Text("Scannen")
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
            }
        }

        // Main FAB. Will be disabled when expanded FAB-Field (see above) ios active
        AnimatedVisibility(visible = !expandFab.value) {
            FloatingActionButton(
                onClick = { expandFab.value = !expandFab.value },
                modifier = Modifier
                    .size(80.dp)
                    .offset(x = (-60).dp, y = (-80).dp),
                shape = CircleShape
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