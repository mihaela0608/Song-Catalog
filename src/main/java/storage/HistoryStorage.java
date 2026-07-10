package storage;

import com.google.gson.Gson;
import model.History;

import java.io.*;


public class HistoryStorage {
    private static final String FILE_NAME = "history.json";
    private final Gson gson = new Gson();


    public boolean save(History history){
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write(gson.toJson(history));
        } catch (IOException exception){
            System.out.println("Error writing to file");
            return false;
        }
        return true;
    }

    public History load(){
        StringBuilder data = new StringBuilder();
        File hisoryFile = new File(FILE_NAME);

        if(!hisoryFile.exists()){
            return new History();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            return new History();
        }



        return gson.fromJson(data.toString(), History.class);
    }
}
