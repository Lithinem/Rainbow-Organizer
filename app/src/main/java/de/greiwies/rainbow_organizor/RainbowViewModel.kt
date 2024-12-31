package de.greiwies.rainbow_organizor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

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