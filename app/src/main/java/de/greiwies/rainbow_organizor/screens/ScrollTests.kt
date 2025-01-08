package de.greiwies.rainbow_organizor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalConfiguration
import kotlin.math.roundToInt

@Composable
fun LazyVerticalGridWithScrollbar(){
    LazyVerticalGridWithScrollbarV5()
}





@Composable
fun LazyVerticalGridWithScrollbarV7() {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100 // Anzahl der Elemente

    // Dynamische Spaltenanzahl basierend auf Bildschirmbreite
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val columns = maxOf(1, (screenWidth / 100.dp).toInt())

    // Berechnung der Scrollbar-Position
    val scrollbarPosition by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val firstVisibleItem = lazyGridState.firstVisibleItemIndex
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                firstVisibleItem.toFloat() / (totalRows - visibleRows)
            } else 0f
        }
    }

    // Dynamische Knob-Höhe basierend auf der Sichtbarkeit
    val knobHeight by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                visibleRows / totalRows
            } else 0.1f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemCount) { index ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "Item $index", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }

        // Scrollbar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
                .align(androidx.compose.ui.Alignment.CenterEnd)
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
        ) {
            // Scrollbar-Indikator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((knobHeight * 300).dp) // Dynamische Höhe basierend auf Sichtbarkeit
                    .offset(y = ((scrollbarPosition * (1 - knobHeight)) * 300).dp) // Offset-Berechnung korrigiert
                    .background(Color.Red, shape = RoundedCornerShape(4.dp))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume() // Verhindert Konflikte mit anderen Gesten
                            coroutineScope.launch {
                                val scaledDrag = dragAmount / knobHeight // Skaliere die Drag-Geste so, dass der Knob der Fingerbewegung folgt
                                lazyGridState.scrollBy(scaledDrag * columns) // Multipliziere mit Spaltenanzahl für korrekte Geschwindigkeit
                            }
                        }
                    }
            )
        }
    }
}



@Composable
fun LazyVerticalGridWithScrollbarV6() {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100 // Anzahl der Elemente

    // Dynamische Spaltenanzahl basierend auf Bildschirmbreite
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val columns = maxOf(1, (screenWidth / 100.dp).toInt())

    // Berechnung der Scrollbar-Position
    val scrollbarPosition by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val firstVisibleItem = lazyGridState.firstVisibleItemIndex
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                firstVisibleItem.toFloat() / (totalRows - visibleRows)
            } else 0f
        }
    }

    // Dynamische Knob-Höhe basierend auf der Sichtbarkeit
    val knobHeight by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                visibleRows / totalRows
            } else 0.1f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemCount) { index ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "Item $index", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }

        // Scrollbar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
                .align(androidx.compose.ui.Alignment.CenterEnd)
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
        ) {
            // Scrollbar-Indikator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((knobHeight * 300).dp) // Dynamische Höhe basierend auf Sichtbarkeit
                    .offset(y = ((scrollbarPosition * (1 - knobHeight)) * 300).dp) // Offset-Berechnung korrigiert
                    .background(Color.Red, shape = RoundedCornerShape(4.dp))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume() // Verhindert Konflikte mit anderen Gesten
                            coroutineScope.launch {
                                val scaledDrag = dragAmount * columns // Skaliere die Drag-Geste mit der Spaltenanzahl
                                lazyGridState.scrollBy(scaledDrag)
                            }
                        }
                    }
            )
        }
    }
}


@Composable
fun LazyVerticalGridWithScrollbarV5() {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100 // Anzahl der Elemente

    // Dynamische Spaltenanzahl basierend auf Bildschirmbreite
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val columns = maxOf(1, (screenWidth / 100.dp).toInt())

    // Berechnung der Scrollbar-Position
    val scrollbarPosition by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val firstVisibleItem = lazyGridState.firstVisibleItemIndex
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                firstVisibleItem.toFloat() / (totalRows - 1)
            } else 0f
        }
    }

    // Dynamische Knob-Höhe basierend auf der Sichtbarkeit
    val knobHeight by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                visibleRows / totalRows
            } else 0.1f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemCount) { index ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "Item $index", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }

        // Scrollbar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
                .align(androidx.compose.ui.Alignment.CenterEnd)
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
        ) {
            // Scrollbar-Indikator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((knobHeight * 300).dp) // Dynamische Höhe basierend auf Sichtbarkeit
                    .offset(y = (scrollbarPosition * (300 - knobHeight * 300)).dp) // Dynamisches Offset
                    .background(Color.Red, shape = RoundedCornerShape(4.dp))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume() // Verhindert Konflikte mit anderen Gesten
                            coroutineScope.launch {
                                val scaledDrag = dragAmount * columns // Skaliere die Drag-Geste mit der Spaltenanzahl
                                lazyGridState.scrollBy(scaledDrag)
                            }
                        }
                    }
            )
        }
    }
}


@Composable
fun LazyVerticalGridWithScrollbarV4() {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100 // Anzahl der Elemente

    // Dynamische Spaltenanzahl basierend auf Bildschirmbreite
    val columns by remember {
        derivedStateOf {
            val itemWidth = 100.dp // Beispielbreite eines Items
            val screenWidth = Dp.Infinity // Annahme: Bildschirmbreite, hier könntest du konkrete Werte einsetzen
            maxOf(1, (screenWidth / itemWidth).toInt())
        }
    }

    // Berechnung der Scrollbar-Position
    val scrollbarPosition by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val firstVisibleItem = lazyGridState.firstVisibleItemIndex
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                firstVisibleItem.toFloat() / totalRows
            } else 0f
        }
    }

    // Dynamische Knob-Höhe basierend auf der Sichtbarkeit
    val knobHeight by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                visibleRows / totalRows
            } else 0.1f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(100.dp),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemCount) { index ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "Item $index", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }

        // Scrollbar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
                .align(androidx.compose.ui.Alignment.CenterEnd)
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
        ) {
            // Scrollbar-Indikator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((knobHeight * 300).dp) // Dynamische Höhe basierend auf Sichtbarkeit
                    .offset(y = (scrollbarPosition * (300 - knobHeight * 300)).dp) // Beispiel-Offset
                    .background(Color.Red, shape = RoundedCornerShape(4.dp))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume() // Verhindert Konflikte mit anderen Gesten
                            coroutineScope.launch {
                                lazyGridState.scrollBy(dragAmount)
                            }
                        }
                    }
            )
        }
    }
}



@Composable
fun LazyVerticalGridWithScrollbarV2() {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100 // Anzahl der Elemente
    val columns = 3 // Anzahl der Spalten

    // Berechnung der Scrollbar-Position
    val scrollbarPosition by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val firstVisibleItem = lazyGridState.firstVisibleItemIndex
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                firstVisibleItem.toFloat() / totalRows
            } else 0f
        }
    }

    // Dynamische Knob-Höhe basierend auf der Sichtbarkeit
    val knobHeight by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                val totalRows = (lazyGridState.layoutInfo.totalItemsCount + columns - 1) / columns
                visibleRows / totalRows
            } else 0.1f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemCount) { index ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "Item $index", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }

        // Scrollbar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
                .align(androidx.compose.ui.Alignment.CenterEnd)
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
        ) {
            // Scrollbar-Indikator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((knobHeight * 300).dp) // Dynamische Höhe basierend auf Sichtbarkeit
                    .offset(y = (scrollbarPosition * (300 - knobHeight * 300)).dp) // Beispiel-Offset
                    .background(Color.Red, shape = RoundedCornerShape(4.dp))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume() // Verhindert Konflikte mit anderen Gesten
                            coroutineScope.launch {
                                lazyGridState.scrollBy(dragAmount)
                            }
                        }
                    }
            )
        }
    }
}


@Composable
fun LazyVerticalGridWithScrollbarV1() {
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100 // Anzahl der Elemente

    // Berechnung der Scrollbar-Position
    val scrollbarPosition by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val firstVisibleItem = lazyGridState.firstVisibleItemIndex
                val totalItems = lazyGridState.layoutInfo.totalItemsCount
                firstVisibleItem.toFloat() / totalItems
            } else 0f
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = lazyGridState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(itemCount) { index ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "Item $index", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }

        // Scrollbar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(16.dp)
                .align(androidx.compose.ui.Alignment.CenterEnd)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        change.consume() // Verhindert Konflikte mit anderen Gesten
                        coroutineScope.launch {
                            lazyGridState.scrollBy(dragAmount)
                        }
                    }
                }
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
        ) {
            // Scrollbar-Indikator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp) // Dynamische Höhe ist optional
                    .offset(y = (scrollbarPosition * 300).dp) // Beispiel-Offset, passt dies für reale Daten an
                    .background(Color.Red
                        , shape = RoundedCornerShape(4.dp))
            )
        }
    }
}
