package com.example.myapplication.ui.add_edit_book

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.utils.UiEvent
import kotlinx.coroutines.flow.collect
import androidx.compose.ui.Alignment.Horizontal

@Composable
fun AddEditBookScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditBookViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditBookEvent.OnSaveBookClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Save"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.genre,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.OnGenreChange(it))
                },
                placeholder = {
                    Text(text = "Genre")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.author,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.OnAuthorChange(it))
                },
                placeholder = {
                    Text(text = "Author")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.quantity,
                onValueChange = {
                    viewModel.onEvent(AddEditBookEvent.OnQuantityChange(it))
                },
                placeholder = {
                    Text(text = "Quantity")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}