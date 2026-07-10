package service;

import model.SearchType;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongServiceTest {

    private SongService songService;


    @BeforeEach
    void setUp() {
        deleteFile("catalog.json");
        deleteFile("history.json");
        songService = new SongService();
    }


    @Test
    void addSong_shouldAddValidSong() {
        Song song = new Song("Kambanes","Argiros",5);

        boolean result = songService.addSong(song);

        assertTrue(result);
        assertEquals(1, songService.getAllSongs().size());
    }


    @Test
    void addSong_shouldNotAddDuplicateSong() {
        Song song1 = new Song("Kambanes","Argiros",5);
        Song song2 = new Song("KAMBANES","ARGIROS",3);
        songService.addSong(song1);

        boolean result = songService.addSong(song2);

        assertFalse(result);
        assertEquals(1,songService.getAllSongs().size());
    }


    @Test
    void addSong_shouldRejectInvalidRating() {
        Song song = new Song("Kambanes","Argiros",8);

        boolean result = songService.addSong(song);

        assertFalse(result);
        assertTrue(songService.getAllSongs().isEmpty());
    }


    @Test
    void removeSong_shouldRemoveExistingSong() {
        Song song = new Song("Kambanes","Argiros",5);
        songService.addSong(song);

        boolean removed = songService.removeSong("Kambanes","Argiros");

        assertTrue(removed);
        assertTrue(songService.getAllSongs().isEmpty());
    }


    @Test
    void removeSong_shouldReturnFalseForMissingSong() {
        boolean result = songService.removeSong("Unknown","Artist");
        assertFalse(result);
    }


    @Test
    void searchSong_shouldFindByPartialTitleIgnoringCase() {
        Song song = new Song("Kambanes","Argiros",5);
        songService.addSong(song);

        List<Song> result = songService.searchSong(SearchType.TITLE,"kam");

        assertEquals(1, result.size());
        assertEquals("Kambanes", result.get(0).getTitle());
    }


    @Test
    void undo_shouldRestorePreviousState() {
        Song song = new Song("Kambanes","Argiros",5);
        songService.addSong(song);

        boolean result = songService.undo();

        assertTrue(result);
        assertTrue(songService.getAllSongs().isEmpty());
    }


    @Test
    void redo_shouldRestoreUndoneChange() {
        Song song = new Song("Kambanes","Argiros",5);
        songService.addSong(song);
        songService.undo();

        boolean result = songService.redo();

        assertTrue(result);
        assertEquals(1, songService.getAllSongs().size());
    }


    private void deleteFile(String fileName) {
        File file = new File(fileName);

        if(file.exists()) {
            file.delete();
        }
    }
}