package service;

import model.Song;
import repository.SongRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class SongService {
    private final SongRepository songRepository;

    public SongService() {
        this.songRepository = new SongRepository();
    }

    public boolean addSong(Song song){
        if (song.getAuthor().isEmpty() || song.getTitle().isEmpty()){
            return false;
        }

        if (song.getRating() > 5 || song.getRating() < 1){
            return false;
        }

        songRepository.add(song);
        return true;
    }

    public boolean removeSong(Song song){
        return songRepository.remove(song);
    }

    public boolean sortSongs(String sortType){
        if (sortType.equalsIgnoreCase("title")){
            Collections.sort(songRepository.getAll(), Comparator.comparing(Song::getTitle));
        } else if (sortType.equalsIgnoreCase("author")) {
            Collections.sort(songRepository.getAll(), Comparator.comparing(Song::getAuthor));
        } else if(sortType.equalsIgnoreCase("rating")){
            Collections.sort(songRepository.getAll(), Comparator.comparing(Song::getRating));
        } else{
            return false;
        }

        return true;
    }

    public Song searchSong(String searchType, String forSearch){
        Song song = null;
        if (searchType.equalsIgnoreCase("title")){
            Optional<Song> optionalSong = songRepository.getAll().stream().filter(s -> s.getTitle().equals(forSearch)).findFirst();
            if (optionalSong.isEmpty()){
                return song;
            }
            song = optionalSong.get();
        } else if (searchType.equalsIgnoreCase("author")) {
            Optional<Song> optionalSong = songRepository.getAll().stream().filter(s -> s.getAuthor().equals(forSearch)).findFirst();
            if (optionalSong.isEmpty()){
                return song;
            }
            song = optionalSong.get();
        }
        return song;
    }
}
