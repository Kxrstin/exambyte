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
    private final Map<TestAufgabe, List<StudiAntwort>> aufgabeMitAntwort = new HashMap<>();

    public StudiTest(TestForm testForm, List<TestAufgabe> testAufgaben) {
        this.testForm = testForm;
        if(testAufgaben != null) {
            this.testAufgaben.addAll(testAufgaben);
            for (TestAufgabe testAufgabe : testAufgaben) {
                aufgabeMitAntwort.put(testAufgabe, new ArrayList<>());
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
        return now.isBefore(testForm.getEndzeitpunkt()) && now.isAfter(testForm.getStartzeitpunkt());
    }

    public boolean isAbgelaufen(LocalDateTime now) {
        return now.isAfter(testForm.getEndzeitpunkt()) && now.isAfter(testForm.getStartzeitpunkt());
    }

    // TestAntworten
    public void addAntwort(String antwort, int aufgabe, int studiId) {
        List<StudiAntwort> studiAntworten = aufgabeMitAntwort.get(testAufgaben.get(aufgabe));

        // Wenn noch keine Studi-Abgabe zur Aufgabe hinzugefügt wurde, wird eine neue StudiAntwort Instanz erstellt
        if(studiAntworten.stream().filter(abgabe -> abgabe.getStudiId() == studiId).findFirst().orElse(null) == null) {
            StudiAntwort neueAbgabe = new StudiAntwort();
            if(isFreitextAufgabe(aufgabe)) {
                neueAbgabe.setFreitextAufgabe(studiId);
            } else if(isMCAufgabe(aufgabe)) {
                neueAbgabe.setMCAufgabe(studiId);
            }
            studiAntworten.add(neueAbgabe);
        }

        // Neue Antwort der Antwortliste zu der zugehörigen Aufgabe hinzufügen, denn studiAntwort kann wegen der vorigen Abfrage nicht null sein
        StudiAntwort studiAntwort = studiAntworten.stream().filter(abgabe -> abgabe.getStudiId() == studiId).findFirst().orElse(null);
        studiAntwort.addAntwort(antwort);
    }

    public String getAntwort(int aufgabe, int studiId) {
        return aufgabeMitAntwort.get(testAufgaben.get(aufgabe)).stream()
                .filter(antwort -> antwort.getStudiId() == studiId)
                .findFirst()
                .orElse(new StudiAntwort())
                .getAntwort();
    }


    // TODO: Das gehört in einen anderen Service aber nicht hier hin
    public boolean testBestanden() {
        return true;
    }
}
