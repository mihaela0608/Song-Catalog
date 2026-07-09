import service.MergeService;
import service.SongService;
import service.UndoRedoService;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SongService songService = new SongService();
        MergeService mergeService = new MergeService();
        UndoRedoService undoRedoService = new UndoRedoService();
        printGreeting();
        displayMenu();
        int choice = scanner.nextInt();

        while (choice != 0){
            switch (choice){
                case 1:

            }
            displayMenu();
            choice = scanner.nextInt();
        }
    }

    private static void printGreeting() {
        System.out.println("=========================================");
        System.out.println("    WELCOME TO SONG CATALOG SYSTEM    ");
        System.out.println("=========================================");
        System.out.println("📀 Your personal music collection manager");
        System.out.println("🎶 Organize, search, and manage your songs");
        System.out.println("=========================================\n");
    }

    private static void displayMenu() {
        System.out.println("\n=========================================");
        System.out.println("            SONG CATALOG MENU           ");
        System.out.println("=========================================");
        System.out.println(" 1. Add song");
        System.out.println(" 2. Remove song");
        System.out.println(" 3. List all songs");
        System.out.println(" 4. Search songs");
        System.out.println(" 5. Merge catalog");
        System.out.println(" 6. Undo");
        System.out.println(" 7. Redo");
        System.out.println(" 0. Exit");
        System.out.println("=========================================");
        System.out.print("Choose option: ");
    }
}
