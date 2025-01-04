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
    var selectedSeriesId = 0
    var selectedBookId = 0

    //Demodaten KI-Generiert
    val demoData = listOf(
        // Naruto-Serie: Teile 1-46
        (1..46)
            .filter { it != 39 } // Filtert Volume 39 heraus
            .map { volume ->
                DataEntry(
                    imageResId = R.drawable::class.java.getField("naruto_volume_$volume")
                        .getInt(null),
                    Series = "Naruto",
                    Title = "Naruto $volume",
                    Languages = listOf("Englisch", "Japanisch"),
                    Summary = "Begleite Naruto Uzumaki, einen jungen Ninja mit großen Träumen, auf seinem Weg, Hokage, der stärkste Ninja seines Dorfes, zu werden. In einer Welt voller Intrigen, Freundschaft und erbitterter Kämpfe muss er seine innere Stärke finden und das Geheimnis des Fuchsgeistes in sich enthüllen.",
                    Pages = 150 + (volume % 3) * 10, // Beispielseitenzahl variiert leicht
                    Volume = volume
                )
            },
        // Neon Genesis Evangelion: Teile 1-4
        (1..4).map { volume ->
            DataEntry(
                imageResId = R.drawable::class.java.getField("evangelion_volume_$volume")
                    .getInt(null),
                Series = "Neon Genesis Evangelion",
                Title = "Evangelion $volume",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "In einer düsteren Zukunft kämpft die Menschheit gegen gigantische Wesen, die als Engel bekannt sind. Die einzige Hoffnung liegt in gigantischen Mechas, den Evangelions, die von mutigen Jugendlichen wie Shinji Ikari gesteuert werden. Eine packende Mischung aus Action, Philosophie und psychologischer Tiefe.",
                Pages = 180 + (volume % 2) * 15, // Beispielseitenzahl
                Volume = volume
            )
        },
        // Noragami: Teile 1-8, 11-12, 14, 18, 26, 27
        listOf(1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 14, 18, 26, 27).map { volume ->
            DataEntry(
                imageResId = R.drawable::class.java.getField("noragami_volume_$volume")
                    .getInt(null),
                Series = "Noragami",
                Title = "Noragami $volume",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Yato, ein pleitegegangener Gott ohne Tempel, nimmt jede Aufgabe an, um sein Leben als Gott zu verbessern – von der Suche nach verlorenen Katzen bis zum Bekämpfen gefährlicher Geister. Mit Witz, Action und einer Prise Drama taucht der Leser in eine Welt ein, in der Götter und Sterbliche Seite an Seite existieren.",
                Pages = 160 + (volume % 4) * 10, // Beispielseitenzahl
                Volume = volume
            )
        },
        // No Guns Life: Teil 1
        listOf(
            DataEntry(
                imageResId = R.drawable::class.java.getField("nogunslife_volume_1").getInt(null),
                Series = "No Guns Life",
                Title = "No Guns Life 1",
                Languages = listOf("Englisch", "Japanisch"),
                Summary = "Juzo Inui, ein Ex-Soldat mit einem Revolver als Kopf, arbeitet in einer Welt, in der Mensch-Maschinen-Hybriden zur Normalität gehören, als Problemlöser. Seine Mission: Ungerechtigkeiten aufdecken und die Geheimnisse seiner mysteriösen Vergangenheit entschlüsseln, während er sich gegen dunkle Mächte stellt.",
                Pages = 200, // Beispielseitenzahl
                Volume = 1
            )
        )
    )


    val demoSeries = demoData
        .flatten()
        .groupBy { it.Series }
        .map { (series, entries) ->
            SeriesSummary(
                series = series,
                totalVolumes = entries.count(),
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
    val Volume: Int
)

data class SeriesSummary(
    @DrawableRes val imageResId: Int,
    val series: String,
    val totalVolumes: Int,
    val totalPages: Int
)