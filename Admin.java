package Verarbeitung;

/**
 * <strong/>Zweck: Gibt Administratoren zusätzlich zu den regulären Nutzer-Attributen die Eigenschaft, löschbar oder nicht löschbar zu sein <strong/>
 * <p><strong>Änderungshistorie:</strong></p>
 * @version 2.0
 * @author Alexander Reichenbach
 *
 */

public class Admin extends Nutzer {

	
    private boolean deletable;

    // Konstruktor: Admin ist löschbar
    public Admin (String name, String password, String sicherheitsFrage, String sicherheitsAntwort) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
        this.deletable = true;
    }

    // erweiterter Konstruktor: dem Admin wird der Parameter deletable übergeben, der bestimmt, ob der Admin löschbar ist oder nicht
    public Admin (String name, String password, String sicherheitsFrage, String sicherheitsAntwort, boolean deletable) {
        super (name, password, sicherheitsFrage, sicherheitsAntwort);
        this.deletable = deletable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}