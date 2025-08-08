package com.example.movietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movietracker.ui.theme.MovieTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTrackerTheme {
                MovieTrackerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTrackerApp() {
    var searchQuery by remember { mutableStateOf("") }
    var movies by remember { mutableStateOf(sampleMovies) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Tracker", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add new movie */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Movie")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search movies...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                singleLine = true
            )

            // Movie List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies.filter { 
                    it.title.contains(searchQuery, ignoreCase = true) 
                }) { movie ->
                    MovieCard(movie = movie)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(movie: Movie) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = movie.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.genre,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rating: ${movie.rating}/5",
                    fontSize = 14.sp
                )
                AssistChip(
                    onClick = { /* TODO: Toggle watched status */ },
                    label = { Text(if (movie.isWatched) "Watched" else "To Watch") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (movie.isWatched) 
                            MaterialTheme.colorScheme.primary 
                        else 
                            MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }
    }
}

data class Movie(
    val title: String,
    val genre: String,
    val rating: Int,
    val isWatched: Boolean
)

val sampleMovies = listOf(
    Movie("The Shawshank Redemption", "Drama", 5, true),
    Movie("The Godfather", "Crime/Drama", 5, true),
    Movie("Pulp Fiction", "Crime/Drama", 4, false),
    Movie("The Dark Knight", "Action/Drama", 5, true),
    Movie("Inception", "Sci-Fi/Thriller", 4, false),
    Movie("Interstellar", "Sci-Fi/Drama", 4, false),
    Movie("The Matrix", "Sci-Fi/Action", 5, true),
    Movie("Forrest Gump", "Drama/Romance", 4, true)
)

@Preview(showBackground = true)
@Composable
fun MovieTrackerAppPreview() {
    MovieTrackerTheme {
        MovieTrackerApp()
    }
}