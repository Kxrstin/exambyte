package exambyte.aggregates.studenten.StudiTest;

import javax.persistence.Column;

public class AntwortMoeglichkeiten {
    private final String antwort;

    @Column(name = "mc_aufgabe")
    private final Integer mcAufgabe;

    public AntwortMoeglichkeiten(String antwort) {
        this(antwort, null);
    }

    public AntwortMoeglichkeiten(String antwort, Integer mcAufgabe) {
        this.antwort = antwort;
        this.mcAufgabe = mcAufgabe;
    }

    public String getAntwortmoeglichkeit() {
        return antwort;
    }

    public Integer getMcAufgabe() {
        return mcAufgabe;
    }
}
