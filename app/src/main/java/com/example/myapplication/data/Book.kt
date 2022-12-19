package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    val title: String,
    val genre: String?,
    val author: String,
    val quantity: Int,
    @PrimaryKey val id: Int? = null
)
