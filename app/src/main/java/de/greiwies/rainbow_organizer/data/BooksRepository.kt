package de.greiwies.rainbow_organizer.data

import de.greiwies.rainbow_organizer.data.entities.Book

class BooksRepository(private val bookDao: BookDao) {
    fun getAll() = bookDao.getAll()
    
    suspend fun insertBook(book: Book) = bookDao.insertBook(book)

    suspend fun deleteBook(book: Book) = bookDao.deleteBook(book)

    suspend fun updateBook(book: Book) = bookDao.updateBook(book)
}