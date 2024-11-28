package exambyte.aggregates.studenten.StudiTest;

import exambyte.aggregates.studenten.StudiAntwort.StudiAntwort;
import exambyte.annotations.AggregateRoot;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AggregateRoot
public final class StudiTest {
    private final TestForm testForm;
    private final List<TestAufgabe> testAufgaben;
    private final Map<TestAufgabe, StudiAntwort> aufgabeMitAntwort = new HashMap<>();

    public StudiTest(TestForm testForm, List<TestAufgabe> testAufgaben) {
        this.testForm = testForm;
        this.testAufgaben = testAufgaben;

        for(TestAufgabe testAufgabe: testAufgaben) {
            aufgabeMitAntwort.put(testAufgabe, new StudiAntwort());

            if(testAufgabe.getClass().equals(FreitextAufgabe.class)) {
                aufgabeMitAntwort.get(testAufgabe).isFreitextAufgabeAufgabe();
            } else {
                aufgabeMitAntwort.get(testAufgabe).isMCAufgabe();
            }
        }
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
    public int getAnzahlAufgaben() {
        return testAufgaben.size();
    }


    // TestAufgaben ausgeben
    public String getAufgabe(int nr) {
        return testAufgaben.get(nr).getAufgabe();
    }

    public int getPunktzahl(int nr) {
        return testAufgaben.get(nr).getPunktzahl();
    }

    public boolean isFreitextAufgabe(int nr) {
        return testAufgaben.get(nr).getClass().equals(FreitextAufgabe.class);
    }

    public boolean isMCAufgabe(int nr) {
        return testAufgaben.get(nr).getClass().equals(MCAufgabe.class);
    }

    public List<String> getAntwortmoeglichkeiten(int nr) {
        if(isMCAufgabe(nr)) {
            return ((MCAufgabe) testAufgaben.get(nr)).getAntwortMoeglichkeiten();
        }
        return List.of();

    }

    // TestAntworten
    public void addAntwort(String antwort, TestAufgabe testAufgabe) {
        StudiAntwort studiAntwort = new StudiAntwort();

        aufgabeMitAntwort.put(testAufgabe, new StudiAntwort());
    }


    // TODO: Das gehört in einen anderen Service aber nicht hier hin
    public boolean testBestanden() {
        return true;
    }
}
