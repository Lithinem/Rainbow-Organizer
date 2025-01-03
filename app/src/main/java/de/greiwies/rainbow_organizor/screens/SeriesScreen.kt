package de.greiwies.rainbow_organizor.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.greiwies.rainbow_organizor.RainbowViewModel
import de.greiwies.rainbow_organizor.components.RainbowScaffold


@Composable
fun DetailsScreen(viewModel: RainbowViewModel) {



    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Selected Item: ${viewModel.selectedSeriesId}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}