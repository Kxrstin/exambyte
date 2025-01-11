package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;

import java.util.List;

@Wertobjekt
public class McAufgabe implements TestAufgabe {
    private final Integer id;
    private final String aufgabe;
    private final String aufgabenstellung;
    private final String titel;
    private final List<AntwortMoeglichkeiten> antwortMoeglichkeiten;
    private final int punktzahl;
    private final Integer studiTest;

    public McAufgabe(String aufgabe, List<AntwortMoeglichkeiten> antwortMoeglichkeiten, int punktzahl, Integer id) {
        this.aufgabe = aufgabe;
        String[] aufgabeMitTitel = aufgabe.split("#%#");
        switch(aufgabeMitTitel.length) {
            case 1:
                this.titel = "";
                this.aufgabenstellung = aufgabeMitTitel[0];
                break;

            case 2:
                this.aufgabenstellung = aufgabeMitTitel[0];
                this.titel = aufgabeMitTitel[1];
                break;

            default:
                this.titel = "";
                this.aufgabenstellung = "";
        }
        this.antwortMoeglichkeiten = antwortMoeglichkeiten;
        this.punktzahl = punktzahl;
        this.id = id;
        this.studiTest = null;
    }

    public McAufgabe(String aufgabe, List<AntwortMoeglichkeiten> antwortMoeglichkeiten, int punktzahl, Integer id, Integer studiTest) {
        this.aufgabe = aufgabe;
        String[] aufgabeMitTitel = aufgabe.split("#%#");
        this.aufgabenstellung = aufgabeMitTitel[0];
        this.titel = aufgabeMitTitel[1];
        this.antwortMoeglichkeiten = antwortMoeglichkeiten;
        this.punktzahl = punktzahl;
        this.id = id;
        this.studiTest = studiTest;
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
        return antwortMoeglichkeiten.stream().map(AntwortMoeglichkeiten::getAntwort).toList();
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
    public String getTitel() {
        return titel;
    }
    public String getAufgabenstellung() {
        return aufgabenstellung;
    }
}
