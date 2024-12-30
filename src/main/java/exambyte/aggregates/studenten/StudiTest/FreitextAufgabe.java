package exambyte.aggregates.studenten.StudiTest;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Id;

public class FreitextAufgabe implements TestAufgabe {
    @Id
    private final Integer id;
    private final String aufgabe;
    private final int punktzahl;

    // TODO: Kein Wertobjekt mehr
    private Integer studiTest;

    @PersistenceCreator
    public FreitextAufgabe(String aufgabe, int punktzahl, Integer id, Integer studiTest) {
        this.aufgabe = aufgabe;
        this.punktzahl = punktzahl;
        this.studiTest = studiTest;
        this.id = id;
    }

    public FreitextAufgabe(String aufgabe, int punktzahl) {
        this(aufgabe, punktzahl, null, null);
    }

    public String getAufgabe() {
        return aufgabe;
    }
    public int getPunktzahl() {
        return punktzahl;
    }
    public Integer getId() {
        return id;
    }
    public boolean isFreitextAufgabe() {
        return true;
    }
}
