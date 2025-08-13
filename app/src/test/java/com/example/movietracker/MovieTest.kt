package com.example.movietracker

import com.example.movietracker.data.Movie
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Stage 8: Added basic unit tests for Movie data class
 */
class MovieTest {

    @Test
    fun movieInitialization_isCorrect() {
        val movie = Movie(
            id = 1,
            title = "Inception",
            category = "Sci-Fi",
            rating = 9,
            isWatched = true,
            notes = "Mind-bending thriller"
        )

        assertEquals(1, movie.id)
        assertEquals("Inception", movie.title)
        assertEquals("Sci-Fi", movie.category)
        assertEquals(9, movie.rating)
        assertTrue(movie.isWatched)
        assertEquals("Mind-bending thriller", movie.notes)
    }

    @Test
    fun defaultValues_areSetCorrectly() {
        val movie = Movie(title = "Interstellar", category = "Sci-Fi", rating = 10)

        assertFalse(movie.isWatched)
        assertEquals("", movie.notes)
    }
}

