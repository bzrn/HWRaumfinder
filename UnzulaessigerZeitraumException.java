package Verarbeitung;

/**
 * <strong>Zweck:</strong> Ausnahmebehandlung eines unzulässigen Zeitraumes (Ende liegt vor Start)
 * @author Alexander Reichenbach
 * @version 1.0
 * 
 */

public class UnzulaessigerZeitraumException extends Exception {
    UnzulaessigerZeitraumException() {
        super ("Ende muss nach Anfang des Zeitraumes liegen");
    }
}
