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

    //Demodaten KI-Generiert
    val demoData = listOf(
        // Naruto-Serie: Teile 1-46
        (1..46).map { volume ->
            DataEntry(
                imageResId = R.drawable::class.java.getField("naruto_volume_$volume").getInt(null),
                Series = "Naruto",
                Title = "Naruto $volume",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 150 + (volume % 3) * 10, // Beispielseitenzahl variiert leicht
                Volume = volume,
                Volumes = 46
            )
        },
        // Neon Genesis Evangelion: Teile 1-4
        (1..4).map { volume ->
            DataEntry(
                imageResId = R.drawable::class.java.getField("evangelion_volume_$volume").getInt(null),
                Series = "Neon Genesis Evangelion",
                Title = "Evangelion $volume",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 180 + (volume % 2) * 15, // Beispielseitenzahl
                Volume = volume,
                Volumes = 4
            )
        },
        // Noragami: Teile 1-8, 11-12, 14, 18, 26, 27
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 14, 18, 26, 27).map { volume ->
            DataEntry(
                imageResId = R.drawable::class.java.getField("noragami_volume_$volume").getInt(null),
                Series = "Noragami",
                Title = "Noragami $volume",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 160 + (volume % 4) * 10, // Beispielseitenzahl
                Volume = volume,
                Volumes = 27
            )
        },
        // No Guns Life: Teil 1
        listOf(
            DataEntry(
                imageResId = R.drawable::class.java.getField("nogunslife_volume_1").getInt(null),
                Series = "No Guns Life",
                Title = "No Guns Life 1",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Lorem Ipsum",
                Pages = 200, // Beispielseitenzahl
                Volume = 1,
                Volumes = 1
            )
        )
    )


    val demoSeries = demoData
        .flatten()
        .groupBy { it.Series }
        .map { (series, entries) ->
            SeriesSummary(
                series = series,
                totalVolumes = entries.sumOf { it.Volumes },
                totalPages = entries.sumOf { it.Pages },
                imageResId = entries.get(0).imageResId
            )
        }

    // Method for publishing an event
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
    @DrawableRes val imageResId: Int,
    val series: String,
    val totalVolumes: Int,
    val totalPages: Int
)