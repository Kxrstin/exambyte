package exambyte.aggregates.studenten.StudiAntwort;

import exambyte.annotations.AggregateRoot;

import java.util.List;


@AggregateRoot
public class StudiAntwort {
    private TestAntwort testAntwort;

    public void isMCAufgabe() {
        testAntwort = new MCAntwort();
    }
    public void isFreitextAufgabeAufgabe() {
        testAntwort = new FreitextAntwort();
    }

    public void addAntwort(String antwort) {
        testAntwort.addAntwort(antwort);
    }

    public void removeAntwort(String antwort) {
        if(testAntwort.isFreitextAufgabe()) {
            ((FreitextAntwort) testAntwort).removeAntwort();
        } else {
            ((MCAntwort) testAntwort).removeAntwort(antwort);
        }
    }

    public List<String> getAntwortWahl() {
        try {
            return ((MCAntwort) testAntwort).getAntwortWahl();
        }
        catch(Exception e) {
            return List.of();
        }
    }

    public String getAntwort() {
        try {
            return ((FreitextAntwort) testAntwort).getAntwort();
        } catch(Exception e) {
            return "";
        }
    }
}
