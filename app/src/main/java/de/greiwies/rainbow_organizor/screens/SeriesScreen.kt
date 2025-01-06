package de.greiwies.rainbow_organizor.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import de.greiwies.rainbow_organizor.DataEntry
import de.greiwies.rainbow_organizor.LocalNavController
import de.greiwies.rainbow_organizor.R
import de.greiwies.rainbow_organizor.RainbowViewModel
import de.greiwies.rainbow_organizor.ui.theme.OverlayBackgroundGrayHalfTransparent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun SeriesScreen(viewModel: RainbowViewModel) {
    if (viewModel.selectedSeriesId < viewModel.demoData.size) {
        ScrollbarExample()
    } else {
        Text("Ups: Da ist etwas bei den Daten schief gelaufen...")
    }
}

@Composable
private fun ScrollbarExample() {
    // State für die LazyColumn
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .interactiveVerticalScrollbar(state = listState) // Scrollbar hinzufügen
        ) {
            items(300) { index ->
                Text(
                    text = "Item $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}


@Composable
private fun DataEntryGrid(viewModel: RainbowViewModel) {
    val selectedVolumes = viewModel.demoData.get(viewModel.selectedSeriesId)
    val context = LocalContext.current
    val tileSizeInDp = with(LocalDensity.current) {
        (context.resources.getInteger(R.integer.BookCoverSize) + context.resources.getInteger(R.integer.BookCoverAdditionalTileEnlargement)).dp
    }
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = tileSizeInDp),
        contentPadding = PaddingValues(8.dp),
        //modifier = Modifier.simpleVerticalScrollbar(state = gridState)
    ) {
        items(selectedVolumes.size) { index ->
            DataEntryTile(viewModel, selectedVolumes.get(index), index)
        }
    }
}

@Composable
private fun DataEntryTile(viewModel: RainbowViewModel, entry: DataEntry, index: Int) {
    val context = LocalContext.current
    val navController = LocalNavController.current
        ?: throw IllegalStateException("NavController not found in the CompositionLocal")
    val bookCoverSizeInDp = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.BookCoverSize).dp
    }
    val outerCornerClipInDp = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.OuterCornerClip).dp
    }
    val innerCornerClipInDp = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.InnerCornerClip).dp
    }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(outerCornerClipInDp))
            .background(OverlayBackgroundGrayHalfTransparent)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = {
                viewModel.selectedBookId = index
                navController.navigate("bookDetails")
            }),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = entry.imageResId),
                contentDescription = entry.Title,
                modifier = Modifier
                    .width(bookCoverSizeInDp)
                    .clip(RoundedCornerShape(innerCornerClipInDp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Band ${entry.Volume}",
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun Modifier.interactiveVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Red,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }.pointerInput(state.layoutInfo.totalItemsCount) {
        coroutineScope {
            detectVerticalDragGestures { change, dragAmount ->
                change.consume() // Verhindert Konflikte mit anderen Modifiern

                val totalItems = state.layoutInfo.totalItemsCount
                val viewportHeight = size.height

                if (totalItems > 0 && viewportHeight > 0) {
                    // Berechne die neue Position basierend auf dem relativen Drag
                    val maxOffset = state.layoutInfo.totalItemsCount * viewportHeight - viewportHeight
                    val scrollOffset = state.firstVisibleItemIndex * viewportHeight

                    // Berechne den Scroll-Offset durch den Drag
                    val newScrollOffset = (scrollOffset + dragAmount).coerceIn(0f, maxOffset.toFloat())

                    // Berechne den neuen Item-Index basierend auf dem neuen Offset
                    val newIndex = (newScrollOffset / viewportHeight).toInt()

                    // Verwende scrollToItem, um zur neuen Position zu springen
                    launch {
                        state.scrollToItem(newIndex)
                    }
                }
            }
        }
    }
}
