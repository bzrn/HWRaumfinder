/**
 * Zweck:
 * @author
 * @version
 * Änderungshistorie:
 * Created by mwolff on 19.06.16.
 * Hanna am 23. Juni
 */


public class Ausstattung {


	//Attribute
	
	private boolean beamer;
	private boolean ohp;
	private boolean tafel;
	private boolean smartboard;
	private boolean whiteboard;
	private boolean computerraum;
	private int kapazitaet;
	
	
	//Konstruktor
	
	public Ausstattung(boolean beamer, boolean ohp, boolean tafel, boolean smartboard, boolean whiteboard,
			boolean computerraum, int kapazitaet) {
		
		this.beamer = beamer;
		this.ohp = ohp;
		this.tafel = tafel;
		this.smartboard = smartboard;
		this.whiteboard = whiteboard;
		this.computerraum = computerraum;
		this.kapazitaet=kapazitaet;
	}

	//Getter und Setter

	public boolean isBeamer() {
		return beamer;
	}


	public void setBeamer(boolean beamer) {
		this.beamer = beamer;
	}


	public boolean isOhp() {
		return ohp;
	}


	public void setOhp(boolean ohp) {
		this.ohp = ohp;
	}


	public boolean isTafel() {
		return tafel;
	}


	public void setTafel(boolean tafel) {
		this.tafel = tafel;
	}


	public boolean isSmartboard() {
		return smartboard;
	}


	public void setSmartboard(boolean smartboard) {
		this.smartboard = smartboard;
	}


	public boolean isWhiteboard() {
		return whiteboard;
	}


	public void setWhiteboard(boolean whiteboard) {
		this.whiteboard = whiteboard;
	}


	public boolean isComputerraum() {
		return computerraum;
	}


	public void setComputerraum(boolean computerraum) {
		this.computerraum = computerraum;
	}

	public int getKapazitaet() {
		return kapazitaet;
	}

	public void setKapazitaet(int kapazitaet) {
		this.kapazitaet = kapazitaet;
	}
	
	/**
	 * 
	 * @param anforderung
	 * @return int erfuellteAnforderungen
	 * 
	 * Es wurde mit der in Java nicht integrierten, logischen Verknüpfung der Implikaition gearbeitet, 
	 * welche mit den vorhandenen logischen Operatoren der Bool'schen Algebra realisiert wurde.
	 */
	
	public int hatMindestens(Ausstattung anforderung){
		
		int erfuellteAnforderungen=0;
		
		if ((anforderung.beamer==true&&anforderung.beamer==this.beamer)
						&& (anforderung.ohp==true&&anforderung.ohp==this.ohp)
						&& (anforderung.tafel==true&&anforderung.tafel==this.tafel)
						&& (anforderung.smartboard==true&&anforderung.smartboard==this.smartboard)
						&& (anforderung.whiteboard==true&&anforderung.whiteboard==this.whiteboard)
						&& (anforderung.computerraum==true&&anforderung.computerraum==this.computerraum)
						&& (anforderung.getKapazitaet()<=this.getKapazitaet()))
		{
			if (this.beamer) erfuellteAnforderungen += 2;
			if (this.ohp) erfuellteAnforderungen++;
			if (this.tafel) erfuellteAnforderungen++;
			if (this.smartboard) erfuellteAnforderungen += 2;
			if (this.whiteboard) erfuellteAnforderungen++;
			if (this.computerraum) erfuellteAnforderungen += 3;
			if (anforderung.getKapazitaet()<=this.getKapazitaet()) erfuellteAnforderungen++;
		}
		
		return erfuellteAnforderungen;
	}
}
