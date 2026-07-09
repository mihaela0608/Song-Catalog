package service;

import model.SearchType;
import model.Song;
import model.SortType;
import repository.SongRepository;

import java.util.*;

public class SongService {
    private final SongRepository songRepository;

    public SongService() {
        this.songRepository = new SongRepository();
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
        return true;
    }

    public boolean removeSong(String title, String author){
        return songRepository.remove(title, author);
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
}
