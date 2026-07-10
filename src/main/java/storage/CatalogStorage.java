package storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Song;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CatalogStorage {
    private static final String FILE_NAME = "catalog.json";
    private final Gson gson = new Gson();


    public boolean save(List<Song> songs){
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write(gson.toJson(songs));
        } catch (IOException exception){
            System.out.println("Error writing to file");
            return false;
        }
        return true;
    }

    public List<Song> load(){
        StringBuilder data = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }

        if(data.isEmpty()){
            return new ArrayList<>();
        }

        Type type = new TypeToken<List<Song>>(){}.getType();

        return gson.fromJson(data.toString(), type);
    }
}
