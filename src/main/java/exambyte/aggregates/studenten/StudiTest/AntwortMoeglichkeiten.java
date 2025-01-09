package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;

@Wertobjekt
public class AntwortMoeglichkeiten {
    private final String antwort;
    private final Integer mcAufgabe;

    public AntwortMoeglichkeiten(String antwort, Integer mcAufgabe) {
        this.antwort = antwort;
        this.mcAufgabe = mcAufgabe;
    }

    public String getAntwort() {
        return antwort;
    }
    public AntwortMoeglichkeiten setAntwort(String antwort) {
        return new AntwortMoeglichkeiten(antwort, mcAufgabe);
    }
    public Integer getMcAufgabe() {
        return mcAufgabe;
    }
}
