package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.AggregateRoot;
import exambyte.annotations.Wertobjekt;

import java.util.ArrayList;
import java.util.List;

@Wertobjekt
public class MCAufgabe implements TestAufgabe {
    private final String aufgabe;
    private final List<String> antwortMoeglichkeiten;
    private final int punktzahl;

    public MCAufgabe(String aufgabe, List<String> antworten, int punktzahl) {
        this.aufgabe = aufgabe;
        this.antwortMoeglichkeiten = antworten;
        this.punktzahl = punktzahl;
    }

    public String getAufgabe() {
        return aufgabe;
    }
    public List<String> getAntwortMoeglichkeiten() {
        return antwortMoeglichkeiten;
    }
    public int getPunktzahl() {
        return punktzahl;
    }
}
