package repository;

import model.Song;

import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    private List<Song> songs;

    public SongRepository() {
        this.songs = new ArrayList<>();
    }

    public void add(Song song){
        songs.add(song);
    }

    public boolean remove(Song song){
        for (int i = 0; i < songs.size(); i++) {
            Song current = songs.get(i);
            if (current.getAuthor().equals(song.getAuthor()) && current.getTitle().equals(song.getTitle())){
                songs.remove(i);
                return true;
            }
        }
        return false;
    }

    public void find(){

    }

    public List<Song> getAll(){
        return songs;
    }

}
