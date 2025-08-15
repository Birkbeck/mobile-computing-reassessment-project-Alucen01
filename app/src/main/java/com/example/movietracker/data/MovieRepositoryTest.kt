/**
 * Repository layer for managing Movie data operations.
 * Acts as an abstraction between the DAO and ViewModels.
 */
package com.example.movietracker.data

import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies()
    }

    suspend fun getMovieById(id: Int): Movie? {
        return movieDao.getMovieById(id)
    }

    suspend fun insertMovie(movie: Movie): Long {
        return movieDao.insertMovie(movie)
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
    }

    suspend fun deleteMovie(movie: Movie) {
        movieDao.deleteMovie(movie)
    }

    fun searchMovies(query: String): Flow<List<Movie>> {
        return movieDao.searchMovies(query)
    }
}
