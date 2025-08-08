package com.example.movietracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movietracker.R
import com.example.movietracker.data.Movie

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val onWatchedToggle: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textView_title)
        private val categoryTextView: TextView = itemView.findViewById(R.id.textView_category)
        private val ratingTextView: TextView = itemView.findViewById(R.id.textView_rating)
        private val watchedTextView: TextView = itemView.findViewById(R.id.textView_watched)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            categoryTextView.text = movie.category
            ratingTextView.text = "Rating: ${movie.rating}/5"
            watchedTextView.text = if (movie.isWatched) "Watched" else "To Watch"
            watchedTextView.setBackgroundResource(
                if (movie.isWatched) R.drawable.bg_watched 
                else R.drawable.bg_to_watch
            )

            itemView.setOnClickListener { onMovieClick(movie) }
            watchedTextView.setOnClickListener { onWatchedToggle(movie) }
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
} 