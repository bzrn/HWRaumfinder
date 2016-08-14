package Verarbeitung;

/**
 * <strong> Zweck:</strong> Gibt Administratoren zusaetzlich zu den regulaeren Nutzer-Attributen die Eigenschaft, loeschbar oder nicht loeschbar zu sein 
 * <h2>Aenderungshistorie:</h2>
 * 
 * <ol>
 * 		<li>
 * 			<ul>
 * 				<li> <strong> Version: </strong> 0.1 </li>
 *				<li> <strong> Datum: </strong> 11.07.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Initiale Befuellung </li>
 *			 </ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 0.2 </li>
 *				<li> <strong> Datum: </strong> 28.07.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Update Package Verarbeitung </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.0 </li>
 *				<li> <strong> Datum: </strong> 07.08.16 </li>
 *				<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 				<li> <strong> Beschreibung: </strong> Finales Update </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 1.1 </li>
 *				<li> <strong> Datum: </strong> 11.08.16 </li>
 *				<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 				<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li>
 *			</ul>
 *		</li>
 *		<li>
 *			<ul>
 *				<li> <strong> Version: </strong> 2.0 </li>
 *				<li> <strong> Datum: </strong> 12.08.16 </li>
 *				<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 				<li> <strong> Beschreibung: </strong> Bug Fixes </li>
 *			</ul>
 *		</li>
 *
 * </ol>

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
