package model;

import lombok.Getter;

@Getter
public class Statistics {

    private final int totalSongs;
    private final int totalArtists;
    private final double averageRating;
    private final Song highestRatedSong;
    private final Song lowestRatedSong;


    public Statistics(
            int totalSongs,
            int totalArtists,
            double averageRating,
            Song highestRatedSong,
            Song lowestRatedSong
    ) {
        this.totalSongs = totalSongs;
        this.totalArtists = totalArtists;
        this.averageRating = averageRating;
        this.highestRatedSong = highestRatedSong;
        this.lowestRatedSong = lowestRatedSong;
    }
}