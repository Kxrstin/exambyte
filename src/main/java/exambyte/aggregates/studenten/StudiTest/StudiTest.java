package exambyte.aggregates.studenten.StudiTest;

import exambyte.aggregates.studenten.StudiAntwort.StudiAntwort;
import exambyte.annotations.AggregateRoot;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AggregateRoot
public final class StudiTest {
    @Column(name = "test_daten")
    private final TestDaten testDaten;

    @Transient
    private final List<TestAufgabe> testAufgaben = new ArrayList<>();

    @Transient
    private final Map<TestAufgabe, List<StudiAntwort>> aufgabeMitAntwort = new HashMap<>();

    @Id
    private Integer id;

    @PersistenceCreator
    public StudiTest(TestDaten testDaten, Integer id) {
        this(testDaten, null, id);
    }

    public StudiTest(TestDaten testDaten, List<TestAufgabe> testAufgaben, Integer id) {
        this.testDaten = testDaten;
        if(testAufgaben != null) {
            this.testAufgaben.addAll(testAufgaben);
            for (TestAufgabe testAufgabe : testAufgaben) {
                aufgabeMitAntwort.put(testAufgabe, new ArrayList<>());
            }
        }
        this.id = id;
    }


    // TestForm Daten ausgeben
    public String getTitel() { return testDaten.getTitel(); }
    public LocalDateTime getEndzeitpunkt() {
        return testDaten.getEndzeitpunkt();
    }
    public LocalDateTime getStartzeitpunkt() {
        return testDaten.getStartzeitpunkt();
    }
    public LocalDateTime getErgebnisZeitpunkt() {
        return testDaten.getErgebniszeitpunkt();
    }
    public int getId() {
        return id;
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
        System.out.println("Ist bearbeitbar wird ausgeführt.");
        return now.isBefore(testDaten.getEndzeitpunkt()) && now.isAfter(testDaten.getStartzeitpunkt());
    }

    public boolean isAbgelaufen(LocalDateTime now) {
        return now.isAfter(testDaten.getEndzeitpunkt()) && now.isAfter(testDaten.getStartzeitpunkt());
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
