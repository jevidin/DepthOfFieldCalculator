package com.example.assignment2.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Data model: Store a collection of lenses.
 */
public class LensManager implements Iterable<Lens>{
    private static LensManager instance;
    private List<Lens> lenses = new ArrayList<>();
    public static LensManager getInstance(){
        if (instance == null){
            instance = new LensManager();
        }
        return instance;
    }
    public void add(Lens lens) {
        lenses.add(lens);
    }
    public void remove(Lens lens) {
        lenses.remove(lens);
    }
    public Lens get(int i) {
        return lenses.get(i);
    }


    @Override
    public Iterator<Lens> iterator() {
        return lenses.iterator();
    }

    public int getNumLenses() {
        return lenses.size();
    }
}
