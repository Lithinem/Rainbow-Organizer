package de.greiwies.rainbow_organizer.data

import android.content.Context

class BookContainer(private val context: Context) {
    val booksRepository: BooksRepository by lazy {
        BooksRepository(BookDatabase.getBookDatabase(context).bookDao())
    }
}