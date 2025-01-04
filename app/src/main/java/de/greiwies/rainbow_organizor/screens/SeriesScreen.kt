package de.greiwies.rainbow_organizor.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.greiwies.rainbow_organizor.DataEntry
import de.greiwies.rainbow_organizor.R
import de.greiwies.rainbow_organizor.RainbowViewModel
import de.greiwies.rainbow_organizor.ui.theme.OverlayBackgroundGrayHalfTransparent


@Composable
fun DetailsScreen(viewModel: RainbowViewModel) {
    if (viewModel.selectedSeriesId < viewModel.demoData.size) {
        val selectedVolumes = viewModel.demoData.get(viewModel.selectedSeriesId)
        DataEntryGrid(selectedVolumes)
    } else {
        Text("Ups: Da ist etwas bei den Daten schief gelaufen...")
    }
}

@Composable
fun DataEntryGrid(entries: List<DataEntry>) {
    val context = LocalContext.current
    val tileSizeInDp = with(LocalDensity.current) {
        (context.resources.getInteger(R.integer.BookCoverSize) + context.resources.getInteger(R.integer.BookCoverAdditionalTileEnlargement)).dp
    }


    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = tileSizeInDp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(entries.size) { index ->
            DataEntryTile(entries.get(index))
        }
    }
}

@Composable
fun DataEntryTile(entry: DataEntry) {
    val context = LocalContext.current
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
            .wrapContentHeight(),
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
