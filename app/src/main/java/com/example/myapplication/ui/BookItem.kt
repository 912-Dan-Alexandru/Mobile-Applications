package com.example.myapplication.ui



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import com.example.myapplication.data.Book
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown

@Composable
fun BookItem(
    book: Book,
    onEvent: (BookListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = book.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {
                    onEvent(BookListEvent.OnDeleteBookClick(book))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                book.genre?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Genre: " + it)
                }
                book.author.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Author: " + it)
                }
                book.quantity.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Quantity: " + it.toString())
                }
                Spacer(modifier = Modifier.height(8.dp))
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = {
                    onEvent(BookListEvent.OnDecreaseBookClick(book))
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Decrement"
                    )
                }
            }
        }
    }
}