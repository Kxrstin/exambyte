package exambyte.aggregates.studenten;

import exambyte.annotations.AggregateRoot;

import java.time.LocalDate;
import java.util.List;

@AggregateRoot
public final class StudiTest {
    TestForm testForm;
    List<TestAufgabe> testAufgaben;

    public StudiTest(TestForm testForm, List<TestAufgabe> testAufgaben) {
        this.testForm = testForm;
        this.testAufgaben = testAufgaben;
    }


    // TestForm Daten ausgeben
    public String getTitel() { return testForm.getTitel(); }
    public LocalDate getEndzeitpunkt() {
        return testForm.getEndzeitpunkt();
    }
    public LocalDate getStartzeitpunkt() {
        return testForm.getEndzeitpunkt();
    }
    public LocalDate getErgebnisZeitpunkt() {
        return testForm.getErgebniszeitpunkt();
    }
    public int getId() {
        return testForm.getTestId();
    }


    // TestAufgaben ausgeben
    public List<String> getTestAufgaben() {
        return testAufgaben.stream()
                .map(n -> n.getAufgabe())
                .toList();
    }

    // TestAntworten
    public void addAntwort(String antwort, TestAufgabe testAufgabe) {
        testAufgabe.addAntwort(antwort);
    }
    public List<String> getAntwortMoeglichkeiten(TestAufgabe aufgabe) {
        if(aufgabe.getClass().equals(MCAufgabe.class)) { return ((MCAufgabe) aufgabe).getAntwortMoeglichkeiten();}
        return List.of();
    }


    // TODO: Das gehört in einen anderen Service aber nicht hier hin
    public boolean testBestanden() {
        return true;
    }
}
