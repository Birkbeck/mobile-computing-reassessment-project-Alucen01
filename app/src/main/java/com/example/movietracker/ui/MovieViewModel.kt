/**
 * ViewModel for managing and preparing Movie data for the UI.
 * Stage 8: Added logging for debugging and updated documentation.
 */

package com.example.movietracker.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietracker.data.Movie
import com.example.movietracker.data.MovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    // StateFlow holding the list of movies
    val movies = repository.getAllMovies()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        Log.d("MovieViewModel", "ViewModel initialized — observing movie data (Stage 8 change)")
    }

    fun addMovie(movie: Movie) {
        Log.d("MovieViewModel", "Adding movie: ${movie.title} (Stage 8 change)")
        viewModelScope.launch {
            repository.insertMovie(movie)
        }
    }

    fun updateMovie(movie: Movie) {
        Log.d("MovieViewModel", "Updating movie: ${movie.title} (Stage 8 change)")
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }

    fun deleteMovie(movie: Movie) {
        Log.d("MovieViewModel", "Deleting movie: ${movie.title} (Stage 8 change)")
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }
}

