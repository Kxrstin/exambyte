package exambyte.aggregates.studenten;

import exambyte.annotations.AggregateRoot;

@AggregateRoot //TODO: ÄNDERN!
public class FreitextAufgabe implements TestAufgabe{
    private final String aufgabe;
    private String antwort;

    public FreitextAufgabe(String aufgabe) {
        this.aufgabe = aufgabe;
    }

    public void addAntwort(String antwort) {
        this.antwort = antwort;
    }
    String getAntwort() {
        return antwort;
    }
    public String getAufgabe() {
        return aufgabe;
    }
}
