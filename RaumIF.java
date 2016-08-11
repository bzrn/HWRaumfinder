package VerarbeitungInterfaces;

import Verarbeitung.Ausstattung;

/**
 * Created by Alex on 11.08.2016.
 */
public interface RaumIF {
    Ausstattung getAusstattung();
    String getRaumBezeichnung();
    boolean isBuchbar();

    void setRaumBezeichnung(String raumBezeichnung);
    void setAusstattung(Ausstattung ausstattung);
    void setAusstattung (boolean beamer, boolean ohp, boolean tafel, boolean smartb, boolean whiteb, boolean computerr, int kapazitaet);
    void setBuchbar (boolean buchbar);
}
