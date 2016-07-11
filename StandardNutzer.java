import java.util.ArrayList;

public class StandardNutzer extends Nutzer implements Reservierer {

	private ArrayList<Reservierung> reservierungen = new ArrayList<Reservierung>();
	
	public StandardNutzer (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
		super (name, password, sicherheitsFrage, sicherheitsAntwort);
	}

	public ArrayList<Reservierung> getReservierungen() {
		return reservierungen;
	}

	public void addReservierung (Reservierung r) {
		reservierungen.add(r);		// NICHT FERTIG
									// Sortiertes Einfuegen fehlt
	}
	
	public void setReservierungen(ArrayList<Reservierung> reservierungen) {
		this.reservierungen = reservierungen;
	}
}