/**
 * Created by Alexander on 25.06.2016.
 */

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

    private String name;

    public Dozent (String name) {
        this.name = name;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
