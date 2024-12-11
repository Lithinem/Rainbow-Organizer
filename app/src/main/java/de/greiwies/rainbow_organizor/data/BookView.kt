package de.greiwies.rainbow_organizor.data

import android.icu.text.DateFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.greiwies.rainbow_organizor.data.entities.Book

import de.greiwies.rainbow_organizor.data.viewModels.BookViewModel
import java.time.LocalDate

@JvmOverloads
@Composable
fun BookView(
    modifier: Modifier = Modifier,
    viewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
){
    val rangesList by viewModel.getAll().collectAsState(initial = emptyList()) // ..2


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the list of friends
        LazyColumn(
            modifier = Modifier.weight(.7F),
            verticalArrangement = Arrangement.Center
        ) {
            items(rangesList) { range ->
                Card(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = range.toString(), style = MaterialTheme.typography.displaySmall)
                    }
                }
            }
        }

        // Input field and buttons
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.5F)
        ) {
            Button(onClick = { viewModel.insertBook(
                Book(12, "Title", "Author1", "Description",
                    12, "Deutsch", "Genres",
                    "12. Edition", false)) }) {
                Text(text = "SAVE")
            }
            Button(onClick = { viewModel.insertBook(
                Book(12, "Title", "Author2", "Description",
                    12, "Deutsch", "Genres",
                    "12. Edition", true)) }) {
                Text(text = "UPDATE")
            }
            Button(onClick = { viewModel.deleteBook(Book(12, "Title", "Author1", "Description",
                12, "Deutsch","Genres",
                "12. Edition", false)) }) {
                Text(text = "DELETE")
            }
        }
    }
}