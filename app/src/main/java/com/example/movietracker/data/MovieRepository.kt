package com.example.movietracker.data

import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    
    fun getAllMovies(): Flow<List<Movie>> = movieDao.getAllMovies()
    
    suspend fun getMovieById(id: Int): Movie? = movieDao.getMovieById(id)
    
    suspend fun insertMovie(movie: Movie): Long = movieDao.insertMovie(movie)
    
    suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(movie)
    
    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)
    
    fun searchMovies(query: String): Flow<List<Movie>> = movieDao.searchMovies(query)
} 