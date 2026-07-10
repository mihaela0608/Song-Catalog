package service;

import model.SearchType;
import model.Song;
import model.SortType;
import repository.SongRepository;
import storage.CatalogStorage;

import java.util.*;

public class SongService {
    private final SongRepository songRepository;
    private final UndoRedoService undoRedoService;
    private final CatalogStorage catalogStorage;

    public SongService() {
        this.catalogStorage = new CatalogStorage();
        this.undoRedoService = new UndoRedoService();
        this.songRepository = new SongRepository(
                undoRedoService.getCurrentState()
        );
    }

    public boolean addSong(Song song){
        if (song.getAuthor().isBlank() || song.getTitle().isBlank()){
            return false;
        }

        if (song.getRating() > 5 || song.getRating() < 1){
            return false;
        }

        boolean exists =
                songRepository.getAll()
                        .stream()
                        .anyMatch(s ->
                                s.getAuthor().equalsIgnoreCase(song.getAuthor()) &&
                                        s.getTitle().equalsIgnoreCase(song.getTitle()));

        if (exists) {
            return false;
        }
        songRepository.add(song);
        undoRedoService.saveSnapshot(songRepository.getAll());
        catalogStorage.save(songRepository.getAll());
        return true;
    }

    public boolean removeSong(String title, String author){
        if (songRepository.remove(title, author)){
            undoRedoService.saveSnapshot(songRepository.getAll());
            catalogStorage.save(songRepository.getAll());
            return true;
        }
        return false;
    }

    public List<Song> sortSongs(SortType sortType){
        List<Song> sorted = new ArrayList<>(songRepository.getAll());
        switch (sortType){
            case TITLE -> sorted.sort(Comparator.comparing(
                    Song::getTitle,
                    String.CASE_INSENSITIVE_ORDER
            ));
            case AUTHOR -> sorted.sort(Comparator.comparing(
                    Song::getAuthor,
                    String.CASE_INSENSITIVE_ORDER
            ));
            case RATING -> sorted.sort(Comparator.comparing(Song::getRating));
        }

        return sorted;
    }

    public List<Song> searchSong(SearchType searchType, String forSearch){
        if(searchType.equals(SearchType.TITLE)) {
            return songRepository.getAll().stream().filter(s -> s.getTitle().toLowerCase().contains(forSearch.toLowerCase())).toList();
        } else if (searchType.equals(SearchType.AUTHOR)){
            return songRepository.getAll().stream().filter(s -> s.getAuthor().toLowerCase().contains(forSearch.toLowerCase())).toList();
        }
        return Collections.emptyList();
    }

    public boolean merge(String fileName){

        List<Song> friendSongs = catalogStorage.load(fileName);

        if (friendSongs == null){
            return false;
        }

        List<Song> mergedSongs = new ArrayList<>(songRepository.getAll());


        for(Song friendSong : friendSongs){
            boolean exists = false;
            for(Song currentSong : mergedSongs){
                if(friendSong.getTitle().equalsIgnoreCase(currentSong.getTitle())
                        && friendSong.getAuthor().equalsIgnoreCase(currentSong.getAuthor())){
                    exists = true;

                    if(friendSong.getRating() > currentSong.getRating()){
                        currentSong.setRating(friendSong.getRating());
                    }
                    break;
                }
            }

            if(!exists){
                mergedSongs.add(friendSong);
            }
        }


        songRepository.replaceSongs(mergedSongs);
        undoRedoService.saveSnapshot(mergedSongs);
        catalogStorage.save(mergedSongs);
        return true;
    }

    public void undo(){
        List<Song> songs = undoRedoService.undo();
        catalogStorage.save(songs);
        songRepository.replaceSongs(songs);
    }

    public void redo(){
        List<Song> songs = undoRedoService.redo();
        catalogStorage.save(songs);
        songRepository.replaceSongs(songs);
    }
}
