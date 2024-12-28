package exambyte.aggregates.studenten.StudiTest;

import org.springframework.data.annotation.Id;
import java.util.List;

public class McAufgabe implements TestAufgabe {
    // TODO Kein Wertobjekt mehr
    @Id
    private Integer id;

    private final String aufgabe;
    private final List<AntwortMoeglichkeiten> antwortMoeglichkeiten;
    private final int punktzahl;
    // TODO Kein Wertobjekt mehr
    private Integer studiTest;

    public McAufgabe(String aufgabe, List<AntwortMoeglichkeiten> antwortMoeglichkeiten, int punktzahl, Integer id) {
        this.aufgabe = aufgabe;
        this.antwortMoeglichkeiten = antwortMoeglichkeiten;
        this.punktzahl = punktzahl;
        this.id = id;
        this.studiTest = null;
    }

    public void setStudiTest(Integer studiTest) {
        this.studiTest = studiTest;
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
    public boolean isFreitextAufgabe() {
        return false;
    }
    public void setId(int id) {
        this.id = id;
    }
}
