 # Angelica Lucena
# Movie Tracker App
this Android app is being developed as part of coursework focused on applying the MVVM architecture using Room, ViewModel, and Repository patterns.

## Features Implemented
- Room database with a `Movie` entity (title, category, rating, watched status, notes)
- `MovieDao` with all required queries (insert, update, delete, search)
- `MovieRepository` to abstract data layer from ViewModel
- `MovieViewModel` using coroutines and `viewModelScope`
- Data flow follows proper MVVM architecture

### 🎯 Technical Implementation

#### Database Layer
- **Room Database**: SQLite database with Room ORM
- **Movie Entity**: Data class with ID, title, category, rating, watched status, and notes
- **MovieDao**: Data Access Object with CRUD operations
- **MovieRepository**: Repository pattern for data operations

#### UI Layer
- **DashboardActivity**: Main screen with RecyclerView showing all movies
- **AddMovieActivity**: Form to add new movies with spinners for category and rating
- **MovieDetailActivity**: Edit movie details with full CRUD operations
- **MovieAdapter**: RecyclerView adapter with DiffUtil for efficient updates

#### Architecture
- MVVM pattern
- Room database
- Repository layer
- Coroutine support
- Reactive `LiveData` used in ViewModel

## Project Structure

```
app/src/main/java/com/example/movietracker/
├── data/
│   ├── Movie.kt                 # Room Entity
│   ├── MovieDao.kt              # Data Access Object
│   ├── MovieDatabase.kt         # Room Database
│   └── MovieRepository.kt       # Repository
├── ui/
│   ├── MovieViewModel.kt        # ViewModel
│   └── MovieAdapter.kt          # RecyclerView Adapter
├── DashboardActivity.kt         # Main Activity
├── AddMovieActivity.kt          # Add Movie Activity
├── MovieDetailActivity.kt       # Edit Movie Activity
└── MovieTrackerApplication.kt   # Application Class
```

## Dependencies Added

- **Room Database**: `androidx.room:room-runtime`, `androidx.room:room-ktx`
- **Material Design**: `com.google.android.material:material`
- **CardView**: `androidx.cardview:cardview`
- **Navigation**: `androidx.navigation:navigation-compose`

## How to Use

1. **Launch App**: Opens to DashboardActivity showing all movies
2. **Add Movie**: Tap the floating action button (+)
3. **View Details**: Tap any movie card to open details
4. **Edit Movie**: Modify fields and tap "Update"
5. **Delete Movie**: Tap "Delete" button with confirmation
6. **Toggle Status**: Tap the watched status chip to toggle

## Database Schema

```sql
CREATE TABLE movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    category TEXT NOT NULL,
    rating INTEGER NOT NULL,
    isWatched BOOLEAN DEFAULT 0,
    notes TEXT DEFAULT ''
);
```

## Features in Detail

### Add Movie
- Title input (EditText)
- Category selection (Spinner with 16 categories)
- Rating selection (Spinner with 1-5 stars)
- Validation for required fields

### Movie List
- RecyclerView with custom adapter
- Card-based layout with movie information
- Watched status indicator with color coding
- Click to edit functionality

### Edit Movie
- Pre-populated form with current movie data
- Update all fields including notes
- Toggle watched status
- Delete with confirmation dialog

### Database Operations
- Insert new movies
- Update existing movies
- Delete movies
- Query all movies
- Search movies by title
- Get movie by ID

## UI/UX Features

- **Material Design**: Cards, FAB, proper spacing
- **Color Coding**: Green for watched, orange for to watch
- **Responsive Layout**: Works on different screen sizes
- **Confirmation Dialogs**: Safe delete operations
- **Form Validation**: Prevents empty submissions
- **Smooth Animations**: RecyclerView with DiffUtil

