package de.greiwies.rainbow_organizor

import androidx.annotation.DrawableRes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.staticCompositionLocalOf

class RainbowViewModel : ViewModel() {
    private val _eventFlow = MutableSharedFlow<String>() // Shared Flow für Events
    val demoEventFLow = _eventFlow.asSharedFlow()

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
    val textValues: List<String>
)

val exampleData = listOf(
    DataEntry(
        imageResId = R.drawable.naruto_42,
        textValues = listOf("Text 1", "Text 2", "Text 3")
    ),
    DataEntry(
        imageResId = R.drawable.naruto_42,
        textValues = listOf("Text A", "Text B")
    )
)