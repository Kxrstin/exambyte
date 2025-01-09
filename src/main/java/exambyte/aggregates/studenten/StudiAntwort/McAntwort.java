package exambyte.aggregates.studenten.StudiAntwort;

import exambyte.annotations.Wertobjekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class McAntwort implements TestAntwort{
    private final Integer id;

    // TODO final
    private String antworten;
    private final Integer aufgabeId;
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

    public McAntwort(Integer id, Integer studiTest, Integer aufgabeId, Integer studiId, String antworten, List<String> antwortWahl)
    {
        this.id = id;
        this.studiTest = studiTest;
        this.aufgabeId = aufgabeId;
        this.studiId = studiId;
        this.antworten = antworten;
        this.antwortWahl = antwortWahl;
    }

    public void addAntwort(String antwort) {
        String listeOhneKlammern = antwort.substring(1, antwort.length()-1);
        Collections.addAll(antwortWahl, listeOhneKlammern.split(", "));
        this.antworten = antwortWahl.toString();
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
    public Integer getAufgabeId() { return aufgabeId; }
    public Integer getStudiTest() {
        return studiTest;
    }
}
