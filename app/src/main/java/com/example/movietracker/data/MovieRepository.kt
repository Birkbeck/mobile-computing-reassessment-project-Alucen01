/**
 * Repository layer for managing Movie data operations.
 * Stage 8: Added documentation and logging for debugging purposes.
 */

package com.example.movietracker.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    fun getAllMovies(): Flow<List<Movie>> {
        Log.d("MovieRepository", "Fetching all movies from database (Stage 8 change)")
        return movieDao.getAllMovies()
    }

    suspend fun getMovieById(id: Int): Movie? = movieDao.getMovieById(id)

    suspend fun insertMovie(movie: Movie): Long = movieDao.insertMovie(movie)

    suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(movie)

    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)

    fun searchMovies(query: String): Flow<List<Movie>> = movieDao.searchMovies(query)
}

