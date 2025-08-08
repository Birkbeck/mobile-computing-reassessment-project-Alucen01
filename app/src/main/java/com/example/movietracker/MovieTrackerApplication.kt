package com.example.movietracker

import android.app.Application
import com.example.movietracker.data.MovieDatabase

class MovieTrackerApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Initialize the database
        MovieDatabase.getDatabase(this)
    }
} 