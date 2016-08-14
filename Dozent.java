
package Verarbeitung;

import java.io.Serializable;

/**
 * <strong>Zweck:</strong> Der Dozent kann als Reservierer angelegt werden, ist selber jedoch kein aktiver Nutzer des Systems. Dadurch ist es moeglich, bei Reservierungen, die Vorlesungen und Seminare repraesentieren, den Dozenten als Reservierer anzugeben. 
 * <h2>Aenderungshistorie:</h2>
 * 
 * <ol>
 * 	<li>
 * 		<ul>
 * 			<li> <strong> Version: </strong> 0.1 </li>
 *			<li> <strong> Datum: </strong> 25.06.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Initiale Befuellung </li>
 *		 </ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 0.2 </li>
 *			<li> <strong> Datum: </strong> 28.07.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Update Package Verarbeitung </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.0 </li>
 *			<li> <strong> Datum: </strong> 06.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Finales Update </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.1 </li>
 *			<li> <strong> Datum: </strong> 11.08.16 </li>
 *			<li> <strong> Autor: </strong> Hanna Behnke </li>
 * 			<li> <strong> Beschreibung: </strong> Kommentare/Unit-Spezifikationen </li>
 *		</ul>
 *	</li>
 *	<li>
 *		<ul>
 *			<li> <strong> Version: </strong> 1.2 </li>
 *			<li> <strong> Datum: </strong> 12.08.16 </li>
 *			<li> <strong> Autor: </strong> Alexander Reichenbach </li>
 * 			<li> <strong> Beschreibung: </strong> Bug Fixes </li>
 *		</ul>
 *	</li>
 * </ol>
 * 
 * @version 1.2
 * @author Alexander Reichenbach
 *
 */

public class Dozent implements Reservierer, Serializable {
	
	// Attribut
    private String name;

    // Kontruktor
    public Dozent (String name) {
        this.name = name;
    }

    // Getter und Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
