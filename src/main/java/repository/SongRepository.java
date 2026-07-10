package repository;

import model.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongRepository {
    private List<Song> songs;

    public SongRepository() {
        this.songs = new ArrayList<>();
    }
    public SongRepository(List<Song> songs){
        this.songs = new ArrayList<>(songs);
    }

    public void add(Song song){
        songs.add(song);
    }

    public boolean remove(String title, String author){
        for (int i = 0; i < songs.size(); i++) {
            Song current = songs.get(i);
            if (current.getTitle().equalsIgnoreCase(title) && current.getAuthor().equalsIgnoreCase(author)){
                songs.remove(i);
                return true;
            }
        }
        return false;
    }
    public void replaceSongs(List<Song> songs){
        this.songs = new ArrayList<>(songs);
    }

    public List<Song> getAll(){
        return Collections.unmodifiableList(songs);
    }

}
