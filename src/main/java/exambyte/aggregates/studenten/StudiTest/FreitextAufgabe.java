package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;

@Wertobjekt
public class FreitextAufgabe implements TestAufgabe {
    private final Integer id;
    private final String aufgabe;
    private final String aufgabenstellung;
    private final String titel;
    private final int punktzahl;
    private final Integer studiTest;

    public FreitextAufgabe(String aufgabe, int punktzahl, Integer id, Integer studiTest) {
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
        this.punktzahl = punktzahl;
        this.studiTest = studiTest;
        this.id = id;
    }

    public FreitextAufgabe(String aufgabe, int punktzahl) {
        this(aufgabe, punktzahl, null, null);
    }

    public String getTitel() {
        return titel;
    }

    public String getAufgabenstellung() {
        return aufgabenstellung;
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
    public Integer getStudiTest() {
        return studiTest;
    }
}
