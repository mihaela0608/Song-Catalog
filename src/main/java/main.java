import model.SearchType;
import model.Song;
import model.SortType;
import model.Statistics;
import service.SongService;

import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        SongService songService = new SongService();

        printGreeting();

        int choice;

        do {
            displayMenu();
            choice = readInt(scanner, "Choose option: ");

            switch (choice) {

                case 1 -> addSong(scanner, songService);
                case 2 -> removeSong(scanner, songService);
                case 3 -> listSongs(scanner, songService);
                case 4 -> searchSongs(scanner, songService);
                case 5 -> mergeCatalog(scanner, songService);
                case 6 -> {
                    if (songService.undo()) {
                        System.out.println("\nUndo completed successfully.");
                    } else {
                        System.out.println("\nNothing to undo.");
                    }
                }
                case 7 -> {
                    if (songService.redo()) {
                        System.out.println("\nRedo completed successfully.");
                    } else {
                        System.out.println("\nNothing to redo.");
                    }
                }
                case 8 -> showStatistics(songService);
                case 0 -> {
                    System.out.println("\nClosing Song Catalog System...");
                }
                default -> System.out.println("\nInvalid option.");

            }

        } while (choice != 0);


        scanner.close();
    }


    private static void addSong(Scanner scanner, SongService songService){

        System.out.println("\n========== ADD SONG ==========");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Artist: ");
        String artist = scanner.nextLine();

        int rating;

        do {
            rating = readInt(scanner, "Rating (1-5): ");

            if(rating < 1 || rating > 5){
                System.out.println("Rating must be between 1 and 5.");
            }

        } while(rating < 1 || rating > 5);

        boolean result = songService.addSong(
                new Song(title, artist, rating)
        );

        if(result){
            System.out.println("Song added successfully.");
        } else {
            System.out.println("Could not add song.");
        }
    }


    private static void removeSong(Scanner scanner, SongService songService){

        System.out.println("\n========== REMOVE SONG ==========");

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Artist: ");
        String artist = scanner.nextLine();


        if(songService.removeSong(title, artist)){
            System.out.println("Song removed successfully.");
        }
        else{
            System.out.println("Song not found.");
        }
    }


    private static void listSongs(Scanner scanner, SongService songService){

        System.out.println("""
                
                ========== LIST SONGS ==========
                
                1. Original order
                2. Sort by title
                3. Sort by artist
                4. Sort by rating
                
                """);


        int option = readInt(scanner, "Choose option: ");
        List<Song> songs;

        switch(option){

            case 1 -> songs = songService.getAllSongs();

            case 2 -> songs = songService.sortSongs(SortType.TITLE);

            case 3 -> songs = songService.sortSongs(SortType.AUTHOR);

            case 4 -> songs = songService.sortSongs(SortType.RATING);

            default -> {
                System.out.println("Invalid option.");
                return;
            }
        }
        printSongs(songs);
    }



    private static void searchSongs(Scanner scanner, SongService songService){

        System.out.println("""
                
                ========== SEARCH ==========
                
                1. Search by title
                2. Search by artist
                
                """);

        int option = readInt(scanner, "Choose option: ");

        SearchType type;
        if(option == 1){
            type = SearchType.TITLE;
        }
        else if(option == 2){
            type = SearchType.AUTHOR;
        }
        else{
            System.out.println("Invalid option.");
            return;
        }


        System.out.print("Search text: ");
        String text = scanner.nextLine();

        List<Song> result = songService.searchSong(type, text);
        printSongs(result);
    }



    private static void mergeCatalog(Scanner scanner, SongService songService){

        System.out.println("\n========== MERGE CATALOG ==========");

        System.out.print("File name: ");
        String file = scanner.nextLine();


        if(songService.merge(file)){
            System.out.println("Catalog merged successfully.");
        }
        else{
            System.out.println("Could not merge catalog.");
        }
    }



    private static void printSongs(List<Song> songs){

        if(songs.isEmpty()){
            System.out.println("\nNo songs found.");
            return;
        }


        System.out.println("""
                
                =================================================================
                
                 #   TITLE                     ARTIST                 RATING
                
                =================================================================
                """);


        int index = 1;

        for(Song song : songs){
            String stars =
                    "★".repeat(song.getRating())
                            +
                            "☆".repeat(5 - song.getRating());

            System.out.printf(
                    "%-3d %-25s %-22s %s%n",
                    index++,
                    song.getTitle(),
                    song.getAuthor(),
                    stars
            );
        }

        System.out.println(
                "================================================================="
        );
    }



    private static void showStatistics(SongService songService){

        Statistics statistics = songService.getStatistics();


        System.out.println("""
            
            ========== STATISTICS ==========
            
            Total songs: %d
            Unique artists: %d
            Average rating: %.2f
            
            Highest rated song:
            
            Lowest rated song:
            
            ================================
            """.formatted(
                statistics.getTotalSongs(),
                statistics.getTotalArtists(),
                statistics.getAverageRating()
        ));


        if(statistics.getHighestRatedSong() != null){

            Song song = statistics.getHighestRatedSong();

            System.out.printf(
                    " %s - %s (%d/5)%n",
                    song.getTitle(),
                    song.getAuthor(),
                    song.getRating()
            );
        }


        if(statistics.getLowestRatedSong() != null){

            Song song = statistics.getLowestRatedSong();

            System.out.printf(
                    " %s - %s (%d/5)%n",
                    song.getTitle(),
                    song.getAuthor(),
                    song.getRating()
            );
        }


        System.out.println(
                "================================"
        );
    }



    private static int readInt(Scanner scanner, String message){

        while(true){

            try{

                System.out.print(message);
                return Integer.parseInt(scanner.nextLine());

            }
            catch(NumberFormatException exception){

                System.out.println("Please enter a valid number.");
            }
        }
    }



    private static void printGreeting(){

        System.out.println("""
                
                =========================================
                    SONG CATALOG SYSTEM
                =========================================
                
                Manage your music collection easily.
                
                =========================================
                """);
    }



    private static void displayMenu(){

        System.out.println("""
                
                ================= MENU =================
                
                1. Add song
                2. Remove song
                3. List songs
                4. Search songs
                5. Merge catalog
                6. Undo
                7. Redo
                8. Statistics
                0. Exit
                
                =========================================
                """);
    }
}