package com.example.movietracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val category: String,
    val rating: Int,
    val isWatched: Boolean = false,
    val notes: String = ""
) 