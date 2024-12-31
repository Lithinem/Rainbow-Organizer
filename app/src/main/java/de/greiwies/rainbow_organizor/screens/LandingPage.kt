package de.greiwies.rainbow_organizor.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun LandingPageContent(modifier: Modifier = Modifier) {
    // Beispiel-Liste mit Textwerten
    val textList = listOf(
        "Willkommen bei unserer App!",
        "Wir freuen uns, dass du hier bist.",
        "Entdecke tolle Funktionen und mehr.",
        "Starte noch heute mit uns.",
        "Willkommen bei unserer App!",
        "Wir freuen uns, dass du hier bist.",
        "Entdecke tolle Funktionen und mehr.",
        "Starte noch heute mit uns.",
        "Willkommen bei unserer App!",
        "Wir freuen uns, dass du hier bist.",
        "Entdecke tolle Funktionen und mehr.",
        "Starte noch heute mit uns.",
        "Willkommen bei unserer App!",
        "Wir freuen uns, dass du hier bist.",
        "Entdecke tolle Funktionen und mehr.",
        "Starte noch heute mit uns.",
        "Willkommen bei unserer App!",
        "Wir freuen uns, dass du hier bist.",
        "Entdecke tolle Funktionen und mehr.",
        "Starte noch heute mit uns.",
        "Willkommen bei unserer App!",
        "Wir freuen uns, dass du hier bist.",
        "Entdecke tolle Funktionen und mehr.",
        "Starte noch heute mit uns.",
        "Bleibe immer auf dem neuesten Stand."
    )

    LazyColumn(modifier = modifier) {
        items(textList.size) { index ->
            val text = textList[index]
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun DetailsScreen(item: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Selected Item: ${item ?: "Unknown"}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


// Derived from https://stackoverflow.com/questions/71657480/alphabetical-scrollbar-in-jetpack-compose
@Composable
fun AlphabeticScrollBar(navController: NavHostController) {
    val items =
        remember { LoremIpsum().values.first().split(" ").plus("ZETA").sortedBy { it.lowercase() } }
    val headers = remember { items.map { it.first().uppercase() }.toSet().toList() }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    var selectedHeaderIndex by remember { mutableStateOf(0) }
    var overlayVisible by remember { mutableStateOf(false) }
    val overlayLetter = remember { mutableStateOf("") }
    val offsets = remember { mutableStateMapOf<Int, Float>() }

    fun updateSelectedIndexIfNeeded(offset: Float) {
        val index = offsets
            .mapValues { abs(it.value - offset) }
            .entries
            .minByOrNull { it.value }
            ?.key ?: return
        if (selectedHeaderIndex == index) return
        selectedHeaderIndex = index
        overlayLetter.value = headers[selectedHeaderIndex]
        overlayVisible = true
        val selectedItemIndex =
            items.indexOfFirst { it.first().uppercase() == headers[selectedHeaderIndex] }
        scope.launch {
            listState.scrollToItem(selectedItemIndex)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        Row {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f)
            ) {
                items(items.size) { index ->
                    Text(
                        text = items[index],
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.clickable {
                            navController.navigate("details/${items[index]}")
                        }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Gray)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            updateSelectedIndexIfNeeded(it.y)
                        }
                    }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, _ ->
                            updateSelectedIndexIfNeeded(change.position.y)
                        }
                    }
            ) {
                headers.forEachIndexed { i, header ->
                    Text(
                        header,
                        modifier = Modifier.onGloballyPositioned {
                            offsets[i] = it.boundsInParent().center.y
                        }
                    )
                }
            }
        }

        // Overlay for selected letter
        AnimatedVisibility(
            visible = overlayVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(width = 8.dp, height = 8.dp)
                    .background(Color(0xAA000000)) // Semi-transparent background
            ) {
                Text(
                    text = overlayLetter.value,
                    style = MaterialTheme.typography.displayLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Auto-hide the overlay after a short delay
        LaunchedEffect(overlayVisible) {
            if (overlayVisible) {
                delay(800) // Overlay disappears after 800ms
                overlayVisible = false
            }
        }
    }
}