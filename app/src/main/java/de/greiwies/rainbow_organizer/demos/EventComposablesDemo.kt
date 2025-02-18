package de.greiwies.rainbow_organizer.demos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.greiwies.rainbow_organizer.RainbowViewModel

@Composable
fun EventComposablesDemoParentComposable(viewModel: RainbowViewModel) {
    // state being used as target of the event
    val events by viewModel.demoEventFLow.collectAsState(initial = "")

    Column(modifier = Modifier.padding(16.dp)) {
        // target of the event being consumed
        Text("Aktuelles Event: $events")

        // button with event emission
        Button(onClick = {
            viewModel.sendEvent("Neues Event von Parent!")
        }) {
            Text("Send Event")
        }

        // Underlying Composable(s)
        ChildComposable(viewModel)
    }
}

@Composable
fun ChildComposable(viewModel: RainbowViewModel) {
    // Another Button emitting the event. This time from another place
    Button(onClick = {
        viewModel.sendEvent("Event von Child")
    }) {
        Text("Event von Child senden")
    }
}