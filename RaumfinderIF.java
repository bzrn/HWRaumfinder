        import java.util.ArrayList;

/**
 * Created by mwolff on 19.06.16.
 */
public interface RaumfinderIF {

    Raum sucheKennung(String raumKennung);

    void addRaum(Raum a);

    ArrayList<Raum> getRaeume();
}
