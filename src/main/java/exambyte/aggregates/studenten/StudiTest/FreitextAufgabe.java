package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.Column;
import org.springframework.data.annotation.Id;

@Wertobjekt
public class FreitextAufgabe implements TestAufgabe {
    @Id
    private final Integer id;

    private final String aufgabe;
    private final int punktzahl;

    // TODO: Kein Wertobjekt mehr
    @Column(name = "studi_test")
    private Integer studiTest;

    @PersistenceCreator
    public FreitextAufgabe(String aufgabe, int punktzahl, Integer id) {
        this.aufgabe = aufgabe;
        this.punktzahl = punktzahl;
        this.studiTest = null;
        this.id = id;
    }

    public FreitextAufgabe(String aufgabe, int punktzahl) {
        this(aufgabe, punktzahl, null);
    }

    public void setStudiTest(Integer studiTest) {
        this.studiTest = studiTest;
    }

    public String getAufgabe() {
        return aufgabe;
    }
    public int getPunktzahl() {
        return punktzahl;
    }
    public int getId() {
        return id;
    }

    public Integer getStudiTest() {
        return studiTest;
    }
}
