package exambyte.aggregates.korrektoren;

import exambyte.annotations.AggregateRoot;

@AggregateRoot
public class Abgabe {
    private final Integer id;
    private final String studiantwort;
    private final String aufgabe;
    private final Integer aufgabenId;
    private final Integer studiId;
    private final String studiTestTitel;
    private String feedback;
    private Integer punktzahl;
    private final Integer maxPunktzahl;
    private final Integer studiTest;
    private final Integer antwort;

    public Abgabe(Integer id, String studiTestTitel, String aufgabe, Integer studiId, Integer aufgabenId, String studiantwort, Integer antwort, int maxPunktzahl, Integer studiTest) {
        this.id = id;
        this.aufgabe = aufgabe;
        this.studiId = studiId;
        this.studiantwort = studiantwort;
        this.studiTestTitel = studiTestTitel;
        this.maxPunktzahl = maxPunktzahl;
        this.studiTest = studiTest;
        this.antwort = antwort;
        this.aufgabenId = aufgabenId;
    }

    public Integer getStudiTest() {
        return studiTest;
    }

    public Integer getAufgabenId() {
        return aufgabenId;
    }

    public Integer getAntwort() {
        return antwort;
    }

    public Integer getStudiId() {
        return studiId;
    }

    public Integer getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getAufgabe() {
        return aufgabe;
    }

    public String getStudiantwort() {
        return studiantwort;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStudiTestTitel() {
        return studiTestTitel;
    }

    public int getMaxPunktzahl() {
        return maxPunktzahl;
    }

    public void setPunktzahl(Integer punktzahl) {
        this.punktzahl=punktzahl;
    }

    public Integer getPunktzahl() {
        return punktzahl;
    }
}
