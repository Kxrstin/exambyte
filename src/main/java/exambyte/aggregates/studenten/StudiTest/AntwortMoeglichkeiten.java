package exambyte.aggregates.studenten.StudiTest;

import javax.persistence.Column;

public class AntwortMoeglichkeiten {
    private String antwort;

    @Column(name = "mc_aufgabe")
    private Integer mcAufgabe;

    public AntwortMoeglichkeiten() {
        this.antwort = null;
        this.mcAufgabe = null;
    }

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
    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }
    public void setMcAufgabe(Integer mcAufgabe) {
        this.mcAufgabe = mcAufgabe;
    }
    public Integer getMcAufgabe() {
        return mcAufgabe;
    }
}
