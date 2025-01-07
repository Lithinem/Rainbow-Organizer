package de.greiwies.rainbow_organizor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun LazyVerticalGridScreen() {
    val gridState = rememberLazyGridState()

    val items = List(100) { "Item #$it" }

    LazyVerticalGridWithScrollbar(
        state = gridState,
        items = items,
        columns = 3, // Anpassen der Anzahl der Spalten
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun LazyVerticalGridWithScrollbar(
    state: LazyGridState,
    items: List<String>,
    columns: Int,
    modifier: Modifier = Modifier
) {
    // LazyVerticalGrid zur Anzeige der Elemente
    Box(modifier = modifier) {
        LazyVerticalGrid(
            state = state,
            columns = GridCells.Fixed(columns),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = items.get(index), color = Color.White)
                }
            }
        }

        // Scrollbar als Overlay
        VerticalScrollbar(
            state = state,

        )
    }
}

@Composable
fun VerticalScrollbar(
    state: LazyGridState,
    width: Dp = 8.dp
) {
    val totalItems = state.layoutInfo.totalItemsCount
    val visibleItems = state.layoutInfo.visibleItemsInfo.size
    val gridHeight = state.layoutInfo.viewportSize.height

    val scrollbarHeight = if (totalItems > 0) {
        (visibleItems / totalItems.toFloat()) * gridHeight
    } else {
        0f
    }

    var scrollbarOffsetY by remember { mutableStateOf(0f) }
    var targetItem by remember { mutableStateOf(0) }

    // LaunchedEffect für das Scrollen
    LaunchedEffect(targetItem) {
        state.scrollToItem(targetItem)
    }

    Box(
        modifier = Modifier
            .width(width)
            .background(Color.Gray.copy(alpha = 0.5f))
            .pointerInput(state) {
                detectVerticalDragGestures { change, dragAmount ->
                    // Gestensteuerung der Scrollbar
                    change.consume()
                    scrollbarOffsetY = (scrollbarOffsetY + dragAmount).coerceIn(0f, gridHeight - scrollbarHeight)

                    // Berechnen der neuen Position der LazyGrid
                    val scrollFraction = scrollbarOffsetY / (gridHeight - scrollbarHeight)
                    targetItem = (scrollFraction * (totalItems - visibleItems)).toInt()
                }
            }
            //    .align(Alignment.Center)

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(scrollbarHeight.dp)
                .offset(y = scrollbarOffsetY.dp)
                .background(Color.Red, shape = RoundedCornerShape(4.dp))
        )
    }
}
