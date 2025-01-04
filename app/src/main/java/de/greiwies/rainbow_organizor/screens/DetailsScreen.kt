package de.greiwies.rainbow_organizor.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.greiwies.rainbow_organizor.RainbowViewModel

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
    Text("Ausgewählt ${viewModel.selectedBookId}")
}