package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;

import javax.persistence.Column;
import java.util.List;

@Wertobjekt
public class McAufgabe implements TestAufgabe {
    @Id
    private Integer id;
    private final String aufgabe;

    @MappedCollection(idColumn = "mc_aufgabe")
    private final List<AntwortMoeglichkeiten> antwortMoeglichkeiten;

    private final int punktzahl;

    @Column(name = "studi_test")
    private Integer studiTest;

    public void setStudiTest(Integer studiTest) {
        this.studiTest = studiTest;
    }

    public McAufgabe(String aufgabe, List<AntwortMoeglichkeiten> antwortMoeglichkeiten, int punktzahl, Integer id) {
        this.aufgabe = aufgabe;
        this.antwortMoeglichkeiten = antwortMoeglichkeiten;
        this.punktzahl = punktzahl;
        this.id = id;
        this.studiTest = null;
    }

    public String getAufgabe() {
        return aufgabe;
    }
    public List<String> getAntwortMoeglichkeiten() {
        return antwortMoeglichkeiten.stream().map(a -> a.getAntwortmoeglichkeit()).toList();
    }
    public int getPunktzahl() {
        return punktzahl;
    }
    public Integer getStudiTest() {
        return studiTest;
    }
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
