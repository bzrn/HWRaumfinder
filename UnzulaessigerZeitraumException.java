package Verarbeitung;

/**
 * Created by Alex on 06.08.2016.
 */
public class UnzulaessigerZeitraumException extends Exception {
    UnzulaessigerZeitraumException() {
        super ("Ende muss nach Anfang des Zeitraumes liegen");
    }
}