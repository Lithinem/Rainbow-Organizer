package de.greiwies.rainbow_organizor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
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

    // LazyColumn für die Liste der Texte
    LazyColumn(modifier = modifier) {
        // Hier verwenden wir `items` richtig
        items(textList.size) { index ->  // Statt `items(textList)` sollte `items(textList.size)` verwendet werden
            val text = textList[index]  // Hole den Text anhand des Index
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// Basis Source from https://stackoverflow.com/questions/71657480/alphabetical-scrollbar-in-jetpack-compose
@Composable
fun AlphabeticScrollBar(){
    val items = remember { LoremIpsum().values.first().split(" ").sortedBy { it.lowercase() } }
    val headers = remember { items.map { it.first().uppercase() }.toSet().toList() }
    Row {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            items(items.size) { index ->
                Text(items.get(index))
            }
        }
        val offsets = remember { mutableStateMapOf<Int, Float>() }
        var selectedHeaderIndex by remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope()

        fun updateSelectedIndexIfNeeded(offset: Float) {
            val index = offsets
                .mapValues { abs(it.value - offset) }
                .entries
                .minByOrNull { it.value }
                ?.key ?: return
            if (selectedHeaderIndex == index) return
            selectedHeaderIndex = index
            val selectedItemIndex = items.indexOfFirst { it.first().uppercase() == headers[selectedHeaderIndex] }
            scope.launch {
                listState.scrollToItem(selectedItemIndex)
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
}