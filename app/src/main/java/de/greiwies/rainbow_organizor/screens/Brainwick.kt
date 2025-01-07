package de.greiwies.rainbow_organizor.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt



//From https://gist.github.com/brianwernick/f6c954e7985357c7b72ceded31b9c34c


@Preview(showBackground = true)
@Composable
private fun PreviewQuickScrollList() {
    QuickScrollList()
}

@Composable
fun QuickScrollList(
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val paddingValues = remember {
        PaddingValues(
            start = 0.dp,
            top = 8.dp,
            end = 0.dp,
            bottom = 56.dp
        )
    }

    val items = remember {
        (0..199).reversed().toList()
    }

    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = paddingValues
        ) {
            items(
                items = items,
                key = { it }
            ) { item ->
                QuickListItem(value = item)
            }
        }

        QuickScroll(
            listState = listState
        )
    }
}

@Composable
private fun QuickListItem(
    value: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(
                start = 8.dp,
                end = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString(),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "bottles of beer on the wall..."
        )
    }
}

/**
 * This is a PoC for a Quick Scroll; this doesn't handle
 * - "locking" the position in relation to the list scroll position (e.g. if you scroll the list the Quick Scroll handle isn't updated)
 * - Assumes all items are of equal height which often isn't the case
 * - Only scrolls to the start of each item (doesn't handle item offsets)
 * - The drag handle doesn't hide / show based on list scroll &/or list length
 *
 * Additionally this hasn't been optimized, so there are likely performance impacts
 */
@Composable
private fun QuickScroll(
    listState: LazyListState
) {
    val scope = rememberCoroutineScope()
    var dragOffsetY by remember { mutableStateOf(0f) }

    var containerHeight by remember { mutableStateOf(0) }
    var handleHeight by remember { mutableStateOf(0) }

    val averageItemHeight = remember(listState.layoutInfo) {
        val viewPortHeight = listState.layoutInfo.viewportEndOffset - listState.layoutInfo.viewportStartOffset
        if (viewPortHeight <= 0) {
            1
        } else {
            //Insert visible items to correct scrollBar size
            viewPortHeight / (listState.layoutInfo.totalItemsCount - listState.layoutInfo.visibleItemsInfo.size)
        }
    }

    val onPositionUpdated: (yPos: Int) -> Unit = {
        val itemIndex = (it / averageItemHeight).coerceIn(0, listState.layoutInfo.totalItemsCount)
        println("ItemIndex: $itemIndex   It: $it    AverageHeight: $averageItemHeight")
        scope.launch {
            // TODO: or listState.animateScrollToItem(itemIndex)
            //listState.scrollToItem(itemIndex)
            listState.scrollBy(30F)
        }
    }

    val maxOffset = remember(containerHeight, handleHeight) {
        (containerHeight - handleHeight).toFloat()
    }

    val dragHandleShape = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 8.dp,
        bottomStart = 8.dp,
        bottomEnd = 0.dp
    )

    Row(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                containerHeight = it.height
            },
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(0, dragOffsetY.roundToInt())
                }
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        dragOffsetY = (dragOffsetY + delta).coerceIn(0f, maxOffset)
                        onPositionUpdated(dragOffsetY.roundToInt())
                    }
                )
                .defaultMinSize(
                    minWidth = 96.dp,
                    minHeight = 48.dp
                )
                .onSizeChanged {
                    handleHeight = it.height
                }
                .clip(dragHandleShape)
                .background(Color.DarkGray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Drag Me",
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}