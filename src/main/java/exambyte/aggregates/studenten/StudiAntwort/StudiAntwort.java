package exambyte.aggregates.studenten.StudiAntwort;

import exambyte.annotations.AggregateRoot;


@AggregateRoot
public class StudiAntwort {
    private TestAntwort testAntwort;
    private int studiId;

    public void setMCAufgabe(int studiId) {
        testAntwort = new MCAntwort();
        this.studiId = studiId;
    }
    public void setFreitextAufgabe(int studiId) {
        testAntwort = new FreitextAntwort();
        this.studiId = studiId;
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

    public int getStudiId() {
        return studiId;
    }
}
