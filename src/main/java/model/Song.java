package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Song {
    private String title;
    private String author;
    private int rating;


    public Song(String title, String author, int rating) {
        this.title = title;
        this.author = author;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return String.format(
                "Title: %s | Artist: %s | Rating: %d",
                title,
                author,
                rating
        );
    }
}
