package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Book
import com.example.myapplication.data.BookRepository
import com.example.myapplication.utils.Routes
import com.example.myapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: BookRepository
): ViewModel() {

    val books = repository.getBooks()

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedBook: Book? = null

    fun onEvent(event: BookListEvent) {
        when(event) {
            is BookListEvent.OnBookClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_BOOK + "?bookId=${event.book.id}"))
            }
            is BookListEvent.OnAddBookClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_BOOK))
            }
            is BookListEvent.OnUndoDeleteClick -> {
                deletedBook?.let { book ->
                    viewModelScope.launch {
                        repository.insertBook(book)
                    }
                }
            }
            is BookListEvent.OnDeleteBookClick -> {
                viewModelScope.launch {
                    deletedBook = event.book
                    repository.deleteBook(event.book)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Book deleted",
                        action = "Undo"
                    ))
                }
            }
            is BookListEvent.OnDecreaseBookClick -> {
                viewModelScope.launch {
                    if (event.book.quantity == 0){
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "There are already 0 copies of this book available",
                        ))
                        return@launch
                    }
                    repository.insertBook(
                        Book(
                            title = event.book.title,
                            genre = event.book.genre,
                            author = event.book.author,
                            quantity = event.book.quantity - 1,
                            id = event.book.id
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}