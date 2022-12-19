package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun insertBook(book: Book)

    suspend fun deleteBook(book: Book)

    suspend fun getBookById(id: Int): Book?

    fun getBooks(): Flow<List<Book>>
}