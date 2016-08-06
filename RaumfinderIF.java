package Verarbeitung;

import Verarbeitung.Raum;

import java.util.ArrayList;

/**
 * Created by mwolff on 19.06.16.
 */
public interface RaumfinderIF {

    Raum sucheKennung(String raumKennung);
    ArrayList<Raum> getRaeume();
    void addRaum(Raum a);
}
