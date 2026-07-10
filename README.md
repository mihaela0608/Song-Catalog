# Song Catalog CLI

A command-line application for managing a song catalog with persistent storage, undo/redo support and catalog merging.

## Features

- Add and remove songs
- List songs with sorting options (title, artist, rating)
- Search songs by title or artist (case-insensitive partial matching)
- Persistent catalog storage
- Undo and redo support across application restarts
- Merge external catalogs
- Catalog statistics

## Technologies

- Java 21
- Maven
- Gson
- Lombok
- JUnit 5

## Project Structure

- **Main** handles user interaction.
- **Service** contains business logic.
- **Repository** manages the catalog collection.
- **Storage** handles JSON file persistence.

## Data Storage

The application stores the current catalog state in:
```
catalog.json
```
Undo and redo history is stored in:
```
history.json
```

## Undo/Redo Design Decision

For implementing undo and redo functionality, one possible approach was creating a separate file for every catalog state(example from AI):

```
state1.json
state2.json
state3.json
...
```

However, this would create unnecessary file management complexity.

The chosen solution was to store all previous catalog states together in a single history file, along with a current index. This allows navigation between previous states while keeping the implementation simpler and cleaner.

## Merge

The application supports importing another catalog file.

When merging catalogs:
- New songs are added.
- Duplicate songs are identified by title and artist.
- If the same song exists, the version with the higher rating is kept.

## Bonus Features

### Statistics

The application provides additional catalog statistics:

- Total number of songs
- Average rating
- Highest rated song
- Lowest rated song
- Rating distribution

### Tests

JUnit tests cover the main service functionality:

- Adding songs
- Removing songs
- Validation
- Searching
- Undo/Redo behavior

## Running the Application

Requirements:

- Java 21+
- Maven

Clone the repository:

```bash
git clone https://github.com/mihaela0608/Song-Catalog
```

Navigate to the project:
```bash
cd song-catalog
```
Build the project:
```bash
mvn clean install
```
Run the application from the main class.

## AI Usage

AI tools were used as a discussion and review tool during development.

One example was the undo/redo architecture. A possible solution was storing every catalog state in a separate file. After considering this approach, I chose to store all states together in one history file with a current index because it provided a cleaner implementation and easier state management.

The final design decisions and implementation were made manually.

## Future Improvements

Possible improvements:

- Replace JSON storage with database storage.
- Introduce dependency injection for better testability.
- Implement a Command Pattern based undo/redo system.
- Add a graphical or web interface.
