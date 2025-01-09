package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;

import java.util.List;

@Wertobjekt
public class McAufgabe implements TestAufgabe {
    private final Integer id;

    private final String aufgabe;
    private final List<AntwortMoeglichkeiten> antwortMoeglichkeiten;
    private final int punktzahl;
    private final Integer studiTest;

    public McAufgabe(String aufgabe, List<AntwortMoeglichkeiten> antwortMoeglichkeiten, int punktzahl, Integer id) {
        this.aufgabe = aufgabe;
        this.antwortMoeglichkeiten = antwortMoeglichkeiten;
        this.punktzahl = punktzahl;
        this.id = id;
        this.studiTest = null;
    }

    public McAufgabe(String aufgabe, List<AntwortMoeglichkeiten> antwortMoeglichkeiten, int punktzahl, Integer id, Integer studiTest) {
        this.aufgabe = aufgabe;
        this.antwortMoeglichkeiten = antwortMoeglichkeiten;
        this.punktzahl = punktzahl;
        this.id = id;
        this.studiTest = null;
    }

    public McAufgabe setStudiTest(Integer studiTest) {
        return new McAufgabe(aufgabe,
                antwortMoeglichkeiten,
                punktzahl, id, studiTest);
    }
    public String getAufgabe() {
        return aufgabe;
    }
    public List<String> getAntwortMoeglichkeitenAlsString() {
        return antwortMoeglichkeiten.stream().map(a -> a.getAntwort()).toList();
    }
    public List<AntwortMoeglichkeiten> getAntwortMoeglichkeiten() {
        return antwortMoeglichkeiten;
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
    public McAufgabe setId(int id) {
        return new McAufgabe(aufgabe,
                antwortMoeglichkeiten,
                punktzahl,
                id,
                studiTest);
    }
}
