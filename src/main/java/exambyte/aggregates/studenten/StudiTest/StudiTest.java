package exambyte.aggregates.studenten.StudiTest;

import exambyte.aggregates.studenten.StudiAntwort.StudiAntwort;
import exambyte.annotations.AggregateRoot;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AggregateRoot
public final class StudiTest {
    private final TestForm testForm;
    private final List<TestAufgabe> testAufgaben = new ArrayList<>();
    private final Map<TestAufgabe, StudiAntwort> aufgabeMitAntwort = new HashMap<>();

    public StudiTest(TestForm testForm, List<TestAufgabe> testAufgaben) {
        this.testForm = testForm;
        if(testAufgaben != null) {
            this.testAufgaben.addAll(testAufgaben);
            for (TestAufgabe testAufgabe : testAufgaben) {
                aufgabeMitAntwort.put(testAufgabe, new StudiAntwort());
            }
        }
    }


    // TestForm Daten ausgeben
    public String getTitel() { return testForm.getTitel(); }
    public LocalDateTime getEndzeitpunkt() {
        return testForm.getEndzeitpunkt();
    }
    public LocalDateTime getStartzeitpunkt() {
        return testForm.getStartzeitpunkt();
    }
    public LocalDateTime getErgebnisZeitpunkt() {
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


    // Zeitpunkte
    public boolean isBearbeitbar(LocalDateTime now) {
        if (now.isBefore(testForm.getEndzeitpunkt()) && now.isAfter(testForm.getStartzeitpunkt())) {
            return true;
        }
        return false;
    }

    public boolean isAbgelaufen(LocalDateTime now) {
        if (now.isAfter(testForm.getEndzeitpunkt()) && now.isAfter(testForm.getStartzeitpunkt())) {
            return true;
        }
        return false;
    }

    // TestAntworten
    public void addAntwort(String antwort, int aufgabe) {
        StudiAntwort studiAntwort = aufgabeMitAntwort.get(testAufgaben.get(aufgabe));
        if(isFreitextAufgabe(aufgabe)) {
            studiAntwort.setFreitextAufgabe();
        } else if(isMCAufgabe(aufgabe)) {
            studiAntwort.setMCAufgabe();
        }
        studiAntwort.addAntwort(antwort);
    }

    public String getAntwort(int aufgabe) {
        return aufgabeMitAntwort.get(testAufgaben.get(aufgabe)).getAntwort();
    }


    // TODO: Das gehört in einen anderen Service aber nicht hier hin
    public boolean testBestanden() {
        return true;
    }
}
