
package Verarbeitung;

import java.io.Serializable;

/**
 * <p><strong>Zweck:</strong> Der Dozent kann als Reservierer angelegt werden, ist selber jedoch kein aktiver Nutzer des Systems. Dadurch ist es möglich, bei Reservierungen, die Vorlesungen und Seminare repräsentieren, den Dozenten als Reservierer anzugeben.</p> 
 * <p><strong>Änderungshistorie:</strong></p>
 * @version 1.3
 * @author Alexander Reichenbach
 *
 */

public class Dozent implements Reservierer, Serializable {
	
	// Attribute
	private static final long serialVersionUID = 5041998765042794345L;
	
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
