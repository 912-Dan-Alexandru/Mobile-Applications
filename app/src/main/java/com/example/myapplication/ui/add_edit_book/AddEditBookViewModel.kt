package com.example.myapplication.ui.add_edit_book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.Book
import com.example.myapplication.data.BookRepository
import com.example.myapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditBookViewModel @Inject constructor(
    private val repository: BookRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var book by mutableStateOf<Book?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var genre by mutableStateOf("")
        private set

    var author by mutableStateOf("")
        private set

    var quantity by mutableStateOf("")
        private set

    private val _uiEvent =  Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val bookId = savedStateHandle.get<Int>("bookId")!!
        if(bookId != -1) {
            viewModelScope.launch {
                repository.getBookById(bookId)?.let { book ->
                    title = book.title
                    genre = book.genre ?: ""
                    author = book.author
                    quantity = book.quantity.toString()
                    this@AddEditBookViewModel.book = book
                }
            }
        }
    }

    fun onEvent(event: AddEditBookEvent) {
        when(event) {
            is AddEditBookEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditBookEvent.OnGenreChange -> {
                genre = event.genre
            }
            is AddEditBookEvent.OnAuthorChange -> {
                author = event.author
            }
            is AddEditBookEvent.OnQuantityChange -> {
                quantity = event.quantity
            }
            is AddEditBookEvent.OnSaveBookClick -> {
                viewModelScope.launch {
                    if(title.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The title can't be empty"
                        ))
                        return@launch
                    }
                    if(quantity.toInt() < 0){
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The quantity can't be negative"
                        ))
                        return@launch
                    }
                    repository.insertBook(
                        Book(
                            title = title,
                            genre = genre,
                            author = author,
                            quantity = quantity.toInt(),
                            id = book?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
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