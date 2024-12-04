package exambyte.aggregates.korrektoren;

import exambyte.annotations.AggregateRoot;

@AggregateRoot
public class Abgabe {
    private final int id;
    private final String studiAntwort;
    private final String aufgabe;
    private final String testname;
    private String feedback;
    private Integer punktzahl;
    private final int maxPunktzahl;

    public Abgabe(int id, String testname, String aufgabe, String studiAntwort, int maxPunktzahl) {
        this.id = id;
        this.aufgabe = aufgabe;
        this.studiAntwort = studiAntwort;
        this.testname = testname;
        this.maxPunktzahl = maxPunktzahl;
    }

    public int getId() {
        return id;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getAufgabe() {
        return aufgabe;
    }

    public String getStudiAntwort() {
        return studiAntwort;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getTestname() {
        return testname;
    }

    public int getMaxPunktzahl() {
        return maxPunktzahl;
    }

    public void setPunktzahl(Integer punktzahl) {
        this.punktzahl = punktzahl;
    }

    public Integer getPunktzahl() {
        return punktzahl;
    }
}
