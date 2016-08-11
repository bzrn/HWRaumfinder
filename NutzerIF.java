package VerarbeitungInterfaces;

/**
 * Created by Alex on 11.08.2016.
 */
public interface NutzerIF {

    String getName();

    void setName(String name);

    String getPwHash();

    void setPwHash(String password);

    String getSicherheitsFrage();

    void setSicherheitsFrage(String sicherheitsFrage);

    String getSicherheitsAntwortHash();

    void setSicherheitsAntwortHash(String sicherheitsAntwort);

    boolean checkPw (String password);

    boolean checkFrage (String antwort);

    boolean isAdmin();
}
