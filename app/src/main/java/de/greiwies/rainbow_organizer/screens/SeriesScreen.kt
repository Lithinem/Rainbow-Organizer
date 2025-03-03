package de.greiwies.rainbow_organizer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.greiwies.rainbow_organizer.DataEntry
import de.greiwies.rainbow_organizer.LocalNavController

import de.greiwies.rainbow_organizer.RainbowViewModel
import de.greiwies.rainbow_organizer.ui.theme.OverlayBackgroundGrayHalfTransparent
import de.greiwies.rainbow_organizer.R
import kotlinx.coroutines.launch


@Composable
fun SeriesScreen(viewModel: RainbowViewModel) {
    if (viewModel.selectedSeriesId < viewModel.demoData.size) {
        DataEntryGrid(viewModel)
    } else {
        Text("Ups: Da ist etwas bei den Daten schief gelaufen...")
    }
}

@Composable
private fun DataEntryGrid(viewModel: RainbowViewModel) {
    var scrollBarHeight by remember { mutableStateOf(0.dp) }

    val selectedVolumes = viewModel.demoData.get(viewModel.selectedSeriesId)
    val context = LocalContext.current
    val tileSizeInDp = with(LocalDensity.current) {
        (context.resources.getInteger(R.integer.BookCoverSize) + context.resources.getInteger(R.integer.BookCoverAdditionalTileEnlargement)).dp
    }
    val scrollBarWidth = context.resources.getInteger(R.integer.ScrollBar_Width).dp
    val lazyGridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val density = LocalDensity.current
    val columns = maxOf(1, (screenWidth / tileSizeInDp).toInt())
    val knobSizePercentage by remember {
        derivedStateOf {
            val viewportHeight = lazyGridState.layoutInfo.viewportEndOffset - lazyGridState.layoutInfo.viewportStartOffset
            val totalContentHeight = lazyGridState.layoutInfo.totalItemsCount.takeIf { it > 0 }?.let {
                lazyGridState.layoutInfo.visibleItemsInfo.firstOrNull()?.size?.height?.times(it / columns)
            } ?: 0

            if (totalContentHeight > 0) {
                viewportHeight.toFloat() / totalContentHeight.toFloat()
            } else {
                1.0f
            }
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

    Box(){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = tileSizeInDp),
            state = lazyGridState,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(selectedVolumes.size) { index ->
                DataEntryTile(viewModel, selectedVolumes.get(index), index)
            }
        }
        // Scrollbar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(scrollBarWidth)
                .align(androidx.compose.ui.Alignment.CenterEnd)
                .padding(4.dp)
                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                .onGloballyPositioned { layoutCoordinates ->
                    scrollBarHeight = with(density) { layoutCoordinates.size.height.toDp() }
                }
        ) {
            // Scrollbar-Indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scrollBarHeight * knobSizePercentage)
                    .offset(y = knobOffset)
                    .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(4.dp))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume()
                            coroutineScope.launch {
                                lazyGridState.scrollBy(dragAmount / knobSizePercentage)
                            }
                        }
                    }
            )
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
            .then(
                if (!entry.IsClickable){
                    Modifier.background(MaterialTheme.colorScheme.errorContainer)
                }
                else Modifier.background(OverlayBackgroundGrayHalfTransparent)
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .then(if (entry.IsClickable) Modifier.clickable {
                viewModel.selectedBookId = index
                navController.navigate("bookDetails")
            } else Modifier),
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
