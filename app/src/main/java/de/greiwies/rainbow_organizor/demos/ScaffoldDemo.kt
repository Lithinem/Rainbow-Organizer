package de.greiwies.rainbow_organizor.demos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldDemo() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meine TopBar") },
                actions = {
                    IconButton(onClick = { /* handle action */ }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorit")
                    }
                }
            )
        },
        content = { paddingValues ->
            // Hier kannst du den Hauptinhalt deiner App hinzufügen
            Text(
                text = "Inhalt der App",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    )
}