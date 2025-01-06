package de.greiwies.rainbow_organizor.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.greiwies.rainbow_organizor.R
import de.greiwies.rainbow_organizor.RainbowViewModel
import de.greiwies.rainbow_organizor.components.TextBlock

@Composable
fun DetailsScreen(viewModel: RainbowViewModel) {
    if (viewModel.selectedSeriesId < viewModel.demoData.size) {
        Content(viewModel)
    } else {
        Text("Ups: Da ist etwas bei den Daten schief gelaufen...")
    }
}

@Composable
private fun Content(viewModel: RainbowViewModel) {
    val datasetToDisplay =
        viewModel.demoData.get(viewModel.selectedSeriesId).get(viewModel.selectedBookId)
    val context = LocalContext.current
    val contentPadding = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.ContentPadding).dp
    }
    val bookCoverSizeInDp = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.BookCoverSize).dp
    }
    val iconSize = with(LocalDensity.current) {
        context.resources.getInteger(R.integer.DefaultIconSize).dp
    }


    val errorText = stringResource(R.string.function_out_of_scope_error)

    Column(modifier = Modifier.padding(contentPadding)) {
        Row {
            Image(
                painter = painterResource(id = datasetToDisplay.imageResId),
                contentDescription = datasetToDisplay.Title,
                modifier = Modifier.width(bookCoverSizeInDp)
            )
            Spacer(modifier = Modifier.size(contentPadding))
            Text(
                text = datasetToDisplay.Title,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.size(contentPadding))
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier.width(bookCoverSizeInDp)
            ) {
                IconButton(onClick = { //* TODO: Implement menu (not Part of Project) */
                    Toast.makeText(context, errorText, Toast.LENGTH_LONG).show()
                }) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        modifier = Modifier.size(iconSize)
                    )
                }
            }
            Spacer(modifier = Modifier.size(contentPadding))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "${datasetToDisplay.Edition}. Ausgabe",
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Band ${datasetToDisplay.Volume}",
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Row {
            Text(
                text = "Zusammenfassung",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Row {
            TextBlock(content = datasetToDisplay.Summary, modifier = Modifier.fillMaxWidth())
        }
    }
}