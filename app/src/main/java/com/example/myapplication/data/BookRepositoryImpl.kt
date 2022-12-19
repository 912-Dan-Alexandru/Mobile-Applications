package com.example.myapplication.data

import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(
    private val dao: BookDao
): BookRepository {

    override suspend fun insertBook(book: Book) {
        dao.insertBook(book)
    }

    override suspend fun deleteBook(book: Book) {
        dao.deleteBook(book)
    }

    override suspend fun getBookById(id: Int): Book? {
        return dao.getBookById(id)
    }

    override fun getBooks(): Flow<List<Book>> {
        return dao.getBooks()
    }
}