package exambyte.aggregates.studenten.StudiAntwort;

import exambyte.annotations.Wertobjekt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class McAntwort implements TestAntwort{
    private final Integer id;
    private final String antworten;
    private final int aufgabeId;
    private final Integer studiId;
    private final Integer studiTest;

    private final List<String> antwortWahl;

    public McAntwort(Integer id, Integer studiTest, Integer aufgabeId, Integer studiId)
    {
        this.id = id;
        this.studiTest = studiTest;
        this.aufgabeId = aufgabeId;
        this.studiId = studiId;
        this.antworten = "";
        this.antwortWahl = new ArrayList<>();
    }

    public McAntwort(Integer id, Integer studiTest, Integer aufgabeId, Integer studiId, String antworten)
    {
        this.id = id;
        this.studiTest = studiTest;
        this.aufgabeId = aufgabeId;
        this.studiId = studiId;
        if(antworten.length() >= 2) {
            String listeOhneKlammern = antworten.substring(1, antworten.length() - 1);
            List<String> neueAntwortWahl = new ArrayList<>(Arrays.stream(listeOhneKlammern.split(", ")).toList());
            this.antworten = neueAntwortWahl.toString();
            this.antwortWahl = neueAntwortWahl;
        } else{
            this.antworten = "";
            this.antwortWahl = new ArrayList<>();
        }

    }

    public McAntwort addAntwort(String antwort) {
        return new McAntwort(id, studiTest, aufgabeId, studiId, antwort);
    }

    public Integer getId(){
        return id;
    }
    public boolean isFreitextAufgabe() {
        return false;
    }
    public Integer getStudiId() {
        return studiId;
    }
    public String getAntworten() {
        return antworten;
    }
    public int getAufgabeId() { return aufgabeId; }
    public Integer getStudiTest() {
        return studiTest;
    }
}
