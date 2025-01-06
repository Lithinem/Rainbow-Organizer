package de.greiwies.rainbow_organizor.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import de.greiwies.rainbow_organizor.data.BookApplication
import de.greiwies.rainbow_organizor.data.BooksRepository
import de.greiwies.rainbow_organizor.data.entities.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BookViewModel(private val booksRepository: BooksRepository) : ViewModel() {
    fun getAll() : Flow<List<Book>> = booksRepository.getAll()

    fun insertBook(book: Book) = viewModelScope.launch {
        booksRepository.insertBook(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch {
        booksRepository.deleteBook(book)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookApplication)
                BookViewModel(application.container.booksRepository)
            }
        }
    }
}