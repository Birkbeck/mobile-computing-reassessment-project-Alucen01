package com.example.movietracker.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movietracker.data.Movie
import com.example.movietracker.data.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies
    
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery
    
    init {
        loadMovies()
    }
    
    fun loadMovies() {
        viewModelScope.launch {
            repository.getAllMovies().collect { movies ->
                _movies.value = movies
            }
        }
    }
    
    fun searchMovies(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            loadMovies()
        } else {
            viewModelScope.launch {
                repository.searchMovies(query).collect { movies ->
                    _movies.value = movies
                }
            }
        }
    }
    
    fun addMovie(title: String, category: String, rating: Int) {
        viewModelScope.launch {
            val movie = Movie(
                title = title,
                category = category,
                rating = rating
            )
            repository.insertMovie(movie)
        }
    }
    
    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }
    
    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }
    
    fun toggleWatchedStatus(movie: Movie) {
        viewModelScope.launch {
            val updatedMovie = movie.copy(isWatched = !movie.isWatched)
            repository.updateMovie(updatedMovie)
        }
    }
    
    suspend fun getMovieById(id: Int): Movie? {
        return repository.getMovieById(id)
    }
}

class MovieViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 