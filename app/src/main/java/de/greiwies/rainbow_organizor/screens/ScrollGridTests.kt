package de.greiwies.rainbow_organizor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MyGridScrollbar(){
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val itemCount = 100
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val density = LocalDensity.current
    val columns = maxOf(1, (screenWidth / 100.dp).toInt())


    // Size of Knob
    var scrollBarHeight by remember { mutableStateOf(0.dp) }


    val knobSizePercentage by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val visibleRows = lazyGridState.layoutInfo.visibleItemsInfo.size / columns.toFloat()
                val totalRows = lazyGridState.layoutInfo.totalItemsCount / columns.toFloat()
                visibleRows / totalRows
            } else 0.1f
        }
    }

    val knobOffset by remember {
        derivedStateOf {
            if (lazyGridState.layoutInfo.totalItemsCount > 0) {
                val firstVisibleRow = lazyGridState.firstVisibleItemIndex / columns.toFloat()
                val totalRows = lazyGridState.layoutInfo.totalItemsCount / columns.toFloat()
                scrollBarHeight * (firstVisibleRow / totalRows)
            } else 0f.dp
        }
    }



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 100.dp),
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
                .onGloballyPositioned { layoutCoordinates ->
                    scrollBarHeight = with(density) { layoutCoordinates.size.height.toDp() }
            }
        ) {
            // Scrollbar-Indikator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scrollBarHeight * knobSizePercentage)
                    .offset(y = knobOffset)
                    .background(Color.Red, shape = RoundedCornerShape(4.dp))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                lazyGridState.scrollBy(dragAmount / knobSizePercentage * 1.15F)
                            }
                        }
                    }
            )
        }
    }
}