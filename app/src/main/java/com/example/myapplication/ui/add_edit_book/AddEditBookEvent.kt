package com.example.myapplication.ui.add_edit_book

sealed class AddEditBookEvent {
    data class OnTitleChange(val title: String): AddEditBookEvent()
    data class OnGenreChange(val genre: String): AddEditBookEvent()
    data class OnAuthorChange(val author: String): AddEditBookEvent()
    data class OnQuantityChange(val quantity: String): AddEditBookEvent()
    object OnSaveBookClick: AddEditBookEvent()
}