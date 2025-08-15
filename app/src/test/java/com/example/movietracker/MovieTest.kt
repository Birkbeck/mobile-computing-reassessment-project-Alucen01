package com.example.movietracker.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Stage 8 - Starting tests for MovieRepository
 * Just basic checks for now, not full coverage.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MovieRepositoryTest {

    private lateinit var repository: MovieRepository
    private lateinit var fakeDao: FakeMovieDao

    @Before
    fun setup() {
        fakeDao = FakeMovieDao()
        repository = MovieRepository(fakeDao)
    }

    @Test
    fun insertMovieAndRetrieveIt() = runTest {
        val movie = Movie(title = "Test Movie", category = "Drama", rating = 5)
        repository.insertMovie(movie)
        val movies = repository.getAllMovies().first()
        assertTrue(movies.contains(movie))
    }

    @Test
    fun searchMoviesReturnsExpectedResults() = runTest {
        val movie1 = Movie(title = "Batman", category = "Action", rating = 4)
        val movie2 = Movie(title = "Barbie", category = "Comedy", rating = 5)
        repository.insertMovie(movie1)
        repository.insertMovie(movie2)

        val results = repository.searchMovies("Barbie").first()
        assertEquals(1, results.size)
        assertEquals("Barbie", results[0].title)
    }


}

class FakeMovieDao : MovieDao {
    private val movies = mutableListOf<Movie>()

    override fun getAllMovies() = kotlinx.coroutines.flow.flow { emit(movies) }

    override suspend fun getMovieById(id: Int) = movies.find { it.id == id }

    override suspend fun insertMovie(movie: Movie): Long {
        movies.add(movie)
        return movie.id.toLong()
    }

    override suspend fun updateMovie(movie: Movie) {
        // not implemented yet
    }

    override suspend fun deleteMovie(movie: Movie) {
        movies.remove(movie)
    }

    override fun searchMovies(query: String) = kotlinx.coroutines.flow.flow {
        emit(movies.filter { it.title.contains(query, ignoreCase = true) })
    }
}

