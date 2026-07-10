package model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class History {
    private List<List<Song>> states;
    private int currentIndex;

    public History() {
        this.states = new ArrayList<>();
        this.states.add(new ArrayList<>());
        this.currentIndex = 0;
    }
}
