package exambyte.aggregates.studenten;

import exambyte.annotations.AggregateRoot;

import java.util.ArrayList;
import java.util.List;

@AggregateRoot //TODO: ÄNDERN!
public class MCAufgabe implements TestAufgabe {
    private final String aufgabe;
    private final List<String> antwortWahl = new ArrayList<>();
    private final List<String> antwortMoeglichkeiten;

    public MCAufgabe(String aufgabe, List<String> antworten) {
        this.aufgabe = aufgabe;
        this.antwortMoeglichkeiten = antworten;
    }

    public String getAufgabe() {
        return aufgabe;
    }
    public void addAntwort(String antwort) {
        antwortWahl.add(antwort);
    }
    public List<String> getAntwortMoeglichkeiten() {
        return antwortMoeglichkeiten;
    }

}
