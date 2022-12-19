package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookById(id: Int): Book?

    @Query("SELECT * FROM book")
    fun getBooks(): Flow<List<Book>>
}