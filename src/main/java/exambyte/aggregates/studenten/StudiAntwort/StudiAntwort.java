package exambyte.aggregates.studenten.StudiAntwort;

import exambyte.annotations.AggregateRoot;


@AggregateRoot
public class StudiAntwort {
    private TestAntwort testAntwort;

    public void setMCAufgabe() {
        testAntwort = new MCAntwort();
    }
    public void setFreitextAufgabe() {
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

    public String getAntwort() {
        try{
            return testAntwort.getAntwort();

        } catch(Exception e) {
            return "";
        }
    }
}
