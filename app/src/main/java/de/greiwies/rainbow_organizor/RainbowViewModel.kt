package de.greiwies.rainbow_organizor

import androidx.annotation.DrawableRes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RainbowViewModel : ViewModel() {
    private val _eventFlow = MutableSharedFlow<String>() // Shared Flow für Events
    val demoEventFLow = _eventFlow.asSharedFlow()

    val demoData = listOf(
        listOf(
            DataEntry(
                imageResId = R.drawable.naruto_42,
                Series = "Naruto",
                Title = "Naruto 1",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 150,
                Volume = 1,
                Volumes = 100

            ),
            DataEntry(
                imageResId = R.drawable.naruto_42,
                Series = "Naruto",
                Title = "Naruto 2",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 132,
                Volume = 2,
                Volumes = 100
            ),
        ),
        listOf(
            DataEntry(
                imageResId = R.drawable.naruto_42,
                Series = "Noragami",
                Title = "Noragami 1",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 150,
                Volume = 1,
                Volumes = 100

            ),
            DataEntry(
                imageResId = R.drawable.naruto_42,
                Series = "Noragami",
                Title = "Noragami 2",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 132,
                Volume = 2,
                Volumes = 100
            ),
        )
    )

    val demoSeries = demoData
        .flatten()
        .groupBy { it.Series }
        .map { (series, entries) ->
            SeriesSummary(
                series = series,
                totalVolumes = entries.sumOf { it.Volumes },
                totalPages = entries.sumOf { it.Pages }
            )
        }

    // Methode, um ein Event zu senden
    fun sendEvent(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(message)
        }
    }
}

val LocalNavController = staticCompositionLocalOf<NavHostController?> {
    null // Initialer Wert, falls nicht gesetzt
}

data class DataEntry(
    @DrawableRes val imageResId: Int,
    val Series: String,
    val Title: String,
    val Languages: List<String>,
    val Summary: String,
    val Pages: Int,
    val Volume: Int,
    val Volumes: Int
)

data class SeriesSummary(
    val series: String,
    val totalVolumes: Int,
    val totalPages: Int
)


data class OldDataEntry(
    @DrawableRes val imageResId: Int,
    val textValues: List<String>
)

val exampleData = listOf(
    OldDataEntry(
        imageResId = R.drawable.naruto_42,
        textValues = listOf("Text 1", "Text 2", "Text 3")
    ),
    OldDataEntry(
        imageResId = R.drawable.naruto_42,
        textValues = listOf("Text A", "Text B")
    )
)