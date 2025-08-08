package com.example.movietracker

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.movietracker.data.MovieDatabase
import com.example.movietracker.data.MovieRepository
import com.example.movietracker.ui.MovieViewModel
import com.example.movietracker.ui.MovieViewModelFactory

class AddMovieActivity : AppCompatActivity() {
    
    private lateinit var viewModel: MovieViewModel
    private lateinit var titleEditText: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var ratingSpinner: Spinner
    private lateinit var saveButton: Button
    
    private val categories = arrayOf(
        "Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary",
        "Drama", "Family", "Fantasy", "Horror", "Mystery", "Romance",
        "Sci-Fi", "Thriller", "War", "Western"
    )
    
    private val ratings = arrayOf("1", "2", "3", "4", "5")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)
        
        setupViewModel()
        setupViews()
        setupSpinners()
        setupSaveButton()
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
        saveButton = findViewById(R.id.button_save)
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
        ratingSpinner.setSelection(4) // Default to 5 stars
    }
    
    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            saveMovie()
        }
    }
    
    private fun saveMovie() {
        val title = titleEditText.text.toString().trim()
        val category = categorySpinner.selectedItem.toString()
        val rating = ratingSpinner.selectedItem.toString().toInt()
        
        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a movie title", Toast.LENGTH_SHORT).show()
            return
        }
        
        viewModel.addMovie(title, category, rating)
        Toast.makeText(this, "Movie added successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }
} 