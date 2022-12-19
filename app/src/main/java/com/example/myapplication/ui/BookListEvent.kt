package com.example.myapplication.ui

import com.example.myapplication.data.Book

sealed class BookListEvent {
    data class OnDeleteBookClick(val book: Book): BookListEvent()
    data class OnDecreaseBookClick(val book: Book): BookListEvent()
    object OnUndoDeleteClick: BookListEvent()
    data class OnBookClick(val book: Book): BookListEvent()
    object OnAddBookClick: BookListEvent()
}
