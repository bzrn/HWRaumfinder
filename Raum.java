import java.util.ArrayList;

/**
 * Zweck:
 * @author
 * @version
 * Änderungshistorie:
 * Created by mwolff on 19.06.16.
 * Hanna am 23. Juni
 */
public class Raum {

    private String raumBezeichnung;
    private ArrayList<Reservierung> belegung;
    private Ausstattung ausstattung;
    private boolean buchbar;


    //Konstruktor - muss die Belegung da rein?
    public Raum(String raumBezeichnung, Ausstattung ausstattung, short kapazitaet, boolean buchbar){
        this.raumBezeichnung = raumBezeichnung;
        this.ausstattung=ausstattung;
        this.belegung = new ArrayList<Reservierung>();
        this.buchbar = buchbar;
    }

    public String getRaumBezeichnung() {
        return raumBezeichnung;
    }

    public void setRaumBezeichnung(String raumBezeichnung) {
        this.raumBezeichnung = raumBezeichnung;
    }

    public ArrayList<Reservierung> getBelegung() {
        return belegung;
    }

    public Ausstattung getAusstattung() {
        return ausstattung;
    }

    public void setAusstattung(Ausstattung ausstattung) {
        this.ausstattung = ausstattung;
    }

    public boolean isBuchbar() {
        return buchbar;
    }
    
    public int hatMindestausstattung (Ausstattung a) {
    	return ausstattung.hatMindestens(a);
    }
}
