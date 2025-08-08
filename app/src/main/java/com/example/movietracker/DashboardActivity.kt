package com.example.movietracker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietracker.data.Movie
import com.example.movietracker.data.MovieDatabase
import com.example.movietracker.data.MovieRepository
import com.example.movietracker.ui.MovieAdapter
import com.example.movietracker.ui.MovieViewModel
import com.example.movietracker.ui.MovieViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardActivity : AppCompatActivity() {
    
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        
        setupViewModel()
        setupRecyclerView()
        setupFab()
        observeMovies()
    }
    
    private fun setupViewModel() {
        val database = MovieDatabase.getDatabase(this)
        val repository = MovieRepository(database.movieDao())
        val factory = MovieViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
    }
    
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_movies)
        movieAdapter = MovieAdapter(
            onMovieClick = { movie -> openMovieDetail(movie) },
            onWatchedToggle = { movie -> viewModel.toggleWatchedStatus(movie) }
        )
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = movieAdapter
        }
    }
    
    private fun setupFab() {
        val fab = findViewById<FloatingActionButton>(R.id.fab_add_movie)
        fab.setOnClickListener {
            openAddMovie()
        }
    }
    
    private fun observeMovies() {
        viewModel.movies.observe(this) { movies ->
            movieAdapter.submitList(movies)
        }
    }
    
    private fun openMovieDetail(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie_id", movie.id)
        }
        startActivity(intent)
    }
    
    private fun openAddMovie() {
        val intent = Intent(this, AddMovieActivity::class.java)
        startActivity(intent)
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.loadMovies()
    }
}
