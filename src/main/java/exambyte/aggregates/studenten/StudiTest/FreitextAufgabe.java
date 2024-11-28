package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;

@Wertobjekt
public class FreitextAufgabe implements TestAufgabe {
    private final String aufgabe;
    private final int punktzahl;

    public FreitextAufgabe(String aufgabe, int punktzahl) {
        this.aufgabe = aufgabe;
        this.punktzahl = punktzahl;
    }

    public String getAufgabe() {
        return aufgabe;
    }
    public int getPunktzahl() {
        return punktzahl;
    }
}
