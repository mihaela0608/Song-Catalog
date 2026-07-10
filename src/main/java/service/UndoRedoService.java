package service;

import model.History;
import model.Song;
import storage.HistoryStorage;

import java.util.ArrayList;
import java.util.List;

public class UndoRedoService {
    private final HistoryStorage historyStorage;
    private final History history;

    public UndoRedoService() {
        this.historyStorage = new HistoryStorage();
        history = historyStorage.load();
    }

    public void saveSnapshot(List<Song> songs){
        if (history.getCurrentIndex() < history.getStates().size() - 1){
            history.getStates()
                    .subList(history.getCurrentIndex() + 1, history.getStates().size())
                    .clear();
        }

        history.getStates().add(new ArrayList<>(songs));
        history.setCurrentIndex(history.getStates().size() - 1);
        historyStorage.save(history);
    }

    public List<Song> undo(){
        if (!canUndo()){
            return null;
        }

        history.setCurrentIndex(history.getCurrentIndex() - 1);
        historyStorage.save(history);
        return new ArrayList<>(history.getStates().get(history.getCurrentIndex()));
    }

    public List<Song> redo(){
        if (!canRedo()){
            return null;
        }

        history.setCurrentIndex(history.getCurrentIndex() + 1);
        historyStorage.save(history);
        return new ArrayList<>(history.getStates().get(history.getCurrentIndex()));
    }

    public List<Song> getCurrentState(){

        if(history.getStates().isEmpty()){
            return new ArrayList<>();
        }

        return new ArrayList<>(
                history.getStates().get(history.getCurrentIndex())
        );
    }

    private boolean canUndo(){
        return history.getCurrentIndex() > 0;
    }

    private boolean canRedo(){
        return history.getCurrentIndex() < history.getStates().size() - 1;
    }
}
