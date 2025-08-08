package com.example.movietracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movietracker.data.Movie
import com.example.movietracker.data.MovieDatabase
import com.example.movietracker.data.MovieRepository
import com.example.movietracker.ui.MovieViewModel
import com.example.movietracker.ui.MovieViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailActivity : AppCompatActivity() {
    
    private lateinit var viewModel: MovieViewModel
    private lateinit var titleEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var ratingSpinner: Spinner
    private lateinit var notesEditText: EditText
    private lateinit var watchedTextView: TextView
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    
    private var currentMovie: Movie? = null
    
    private val categories = arrayOf(
        "Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary",
        "Drama", "Family", "Fantasy", "Horror", "Mystery", "Romance",
        "Sci-Fi", "Thriller", "War", "Western"
    )
    
    private val ratings = arrayOf("1", "2", "3", "4", "5")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        
        setupViewModel()
        setupViews()
        setupSpinners()
        loadMovie()
        setupButtons()
    }
    
    private fun setupViewModel() {
        val database = MovieDatabase.getDatabase(this)
        val repository = MovieRepository(database.movieDao())
        val factory = MovieViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }
    
    private fun setupViews() {
        titleEditText = findViewById(R.id.editText_title)
        categorySpinner = findViewById(R.id.spinner_category)
        ratingSpinner = findViewById(R.id.spinner_rating)
        notesEditText = findViewById(R.id.editText_notes)
        watchedTextView = findViewById(R.id.textView_watched)
        updateButton = findViewById(R.id.button_update)
        deleteButton = findViewById(R.id.button_delete)
    }
    
    private fun setupSpinners() {
        // Setup category spinner
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter
        
        // Setup rating spinner
        val ratingAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ratings)
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ratingSpinner.adapter = ratingAdapter
    }
    
    private fun loadMovie() {
        val movieId = intent.getIntExtra("movie_id", -1)
        if (movieId == -1) {
            Toast.makeText(this, "Error loading movie", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        CoroutineScope(Dispatchers.Main).launch {
            currentMovie = withContext(Dispatchers.IO) {
                viewModel.getMovieById(movieId)
            }
            
            currentMovie?.let { movie ->
                titleEditText.setText(movie.title)
                categorySpinner.setSelection(categories.indexOf(movie.category))
                ratingSpinner.setSelection(movie.rating - 1)
                notesEditText.setText(movie.notes)
                updateWatchedStatus(movie.isWatched)
            }
        }
    }
    
    private fun updateWatchedStatus(isWatched: Boolean) {
        watchedTextView.text = if (isWatched) "Watched" else "To Watch"
        watchedTextView.setBackgroundResource(
            if (isWatched) R.drawable.bg_watched else R.drawable.bg_to_watch
        )
    }
    
    private fun setupButtons() {
        updateButton.setOnClickListener {
            updateMovie()
        }
        
        deleteButton.setOnClickListener {
            showDeleteConfirmation()
        }
        
        watchedTextView.setOnClickListener {
            toggleWatchedStatus()
        }
    }
    
    private fun updateMovie() {
        val title = titleEditText.text.toString().trim()
        val category = categorySpinner.selectedItem.toString()
        val rating = ratingSpinner.selectedItem.toString().toInt()
        val notes = notesEditText.text.toString().trim()
        
        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a movie title", Toast.LENGTH_SHORT).show()
            return
        }
        
        currentMovie?.let { movie ->
            val updatedMovie = movie.copy(
                title = title,
                category = category,
                rating = rating,
                notes = notes
            )
            viewModel.updateMovie(updatedMovie)
            Toast.makeText(this, "Movie updated successfully!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun toggleWatchedStatus() {
        currentMovie?.let { movie ->
            val updatedMovie = movie.copy(isWatched = !movie.isWatched)
            viewModel.updateMovie(updatedMovie)
            updateWatchedStatus(updatedMovie.isWatched)
        }
    }
    
    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Delete Movie")
            .setMessage("Are you sure you want to delete this movie?")
            .setPositiveButton("Delete") { _, _ ->
                deleteMovie()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
    
    private fun deleteMovie() {
        currentMovie?.let { movie ->
            viewModel.deleteMovie(movie)
            Toast.makeText(this, "Movie deleted successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
    
    private suspend fun getMovieById(id: Int): Movie? {
        return withContext(Dispatchers.IO) {
            viewModel.getMovieById(id)
        }
    }
} 