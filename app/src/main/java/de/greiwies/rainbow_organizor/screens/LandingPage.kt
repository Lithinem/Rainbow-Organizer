package de.greiwies.rainbow_organizor.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import de.greiwies.rainbow_organizor.LocalNavController
import de.greiwies.rainbow_organizor.R
import de.greiwies.rainbow_organizor.RainbowViewModel
import de.greiwies.rainbow_organizor.SeriesSummary
import de.greiwies.rainbow_organizor.components.VisibleSeparator
import de.greiwies.rainbow_organizor.ui.theme.OverlayBackgroundGrayHalfTransparent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun LandingPage(viewModel: RainbowViewModel)
{
    val items = remember { LoremIpsum().values.first().split(" ").plus("ZETA").sortedBy { it.lowercase() } }
    //LandingPageContent(viewModel)
    //LazyVerticalGridWithScrollbar()
    //QuickScrollList()
    MyGridScrollbar()
}


// Derived from https://stackoverflow.com/questions/71657480/alphabetical-scrollbar-in-jetpack-compose
@Composable
private fun LandingPageContent(viewModel: RainbowViewModel) {
    val bookSeries = viewModel.demoSeries
    val headers = remember { bookSeries.map { it.series.first().uppercase() }.toSet().toList() }

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
            bookSeries.indexOfFirst { it.series.first().uppercase() == headers[selectedHeaderIndex] }
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
                items(bookSeries.size) { index ->
                    ContentElement(bookSeries[index], index, viewModel)
                    //TODO: Delete
                    //Text(
                    //    text = items[index],
                    //    style = MaterialTheme.typography.bodyMedium,
                    //    modifier = Modifier.clickable {
                    //        navController.navigate("details/${items[index]}")
                    //    }
                    //)
                    //Spacer(modifier = Modifier.padding(10.dp))
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
                    .fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp) // Größe des Overlays
                        .background(Color.DarkGray, shape = CircleShape) // Inner Circle
                ) {
                    Text(
                        text = overlayLetter.value,
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
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

@Composable
private fun ContentElement(item: SeriesSummary, index:Int, viewModel: RainbowViewModel){
    val navController = LocalNavController.current
        ?: throw IllegalStateException("NavController not found in the CompositionLocal")

    val context = LocalContext.current
    val bookCoverSizeInDp = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.BookCoverSize).dp
    }

    Box(modifier = Modifier
        .clickable {
            viewModel.selectedSeriesId = index
            navController.navigate("series")
        }
        .fillMaxWidth())
    {
        Column {
            Spacer(modifier = Modifier.padding(5.dp))

            Row {
                Image(
                    painter = painterResource(id = item.imageResId),
                    contentDescription = "Manga Image",
                    modifier = Modifier
                        .width(bookCoverSizeInDp)
                        .padding(end = 5.dp),
                    contentScale = ContentScale.Fit
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1F),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = item.series,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.End
                    )
                    Spacer(modifier = Modifier.size(30.dp))
                    Text(
                        text = "Insgesamt",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.End
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = item.totalPages.toString() + " Seiten",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = (
                                if (item.totalVolumes < 2)
                                    item.totalVolumes.toString() + " Band"
                                else
                                    item.totalVolumes.toString() + " Bände"
                                ),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End
                    )
                }
            }

            Spacer(modifier = Modifier.padding(5.dp))

            VisibleSeparator()
        }
    }
}