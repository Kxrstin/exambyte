package exambyte.aggregates.studenten.StudiTest;

import exambyte.aggregates.studenten.StudiAntwort.FreitextAntwort;
import exambyte.aggregates.studenten.StudiAntwort.McAntwort;
import exambyte.annotations.AggregateRoot;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.*;

@AggregateRoot
public final class StudiTest {
    @Id
    private Integer id;
    private final TestDaten testDaten;
    private List<FreitextAufgabe> freitextAufgaben = new ArrayList<>();
    private List<McAufgabe> mcAufgaben = new ArrayList<>();
    private List<McAntwort> mcAntworten = new ArrayList<>();
    private List<FreitextAntwort> freitextAntworten = new ArrayList<>();

    @Transient
    private final List<TestAufgabe> testAufgaben = new ArrayList<>();

    public StudiTest(Integer id, TestDaten testDaten) {
        this(id, testDaten, null, null);
    }

    @PersistenceCreator
    public StudiTest(Integer id, TestDaten testDaten, List<McAufgabe> mcAufgaben, List<FreitextAufgabe> freitextAufgaben) {
        this.id = id;
        this.testDaten = testDaten;
        if(mcAufgaben != null) {
            this.mcAufgaben = mcAufgaben;
            this.testAufgaben.addAll(mcAufgaben);
        }
        if(freitextAufgaben != null) {
            this.freitextAufgaben = freitextAufgaben;
            this.testAufgaben.addAll(freitextAufgaben);
        }
    }


    // TestForm Daten
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
        return freitextAufgaben.size() + mcAufgaben.size();
    }


    // TestAufgaben
    public String getAufgabe(int aufgabenId) {
        return testAufgaben.stream()
                .filter(aufgabe -> aufgabe.getId() == aufgabenId)
                .map(TestAufgabe::getAufgabe)
                .findFirst()
                .orElse("");
    }
    public int getPunktzahl(int aufgabenId) {
        return testAufgaben.stream()
                .filter(aufgabe -> aufgabe.getId() == aufgabenId)
                .map(TestAufgabe::getPunktzahl)
                .findFirst()
                .orElse(-1);
    }
    public boolean isFreitextAufgabe(int aufgabenId) {
        return testAufgaben.stream()
                .filter(aufgabe -> aufgabe.getId() == aufgabenId)
                .map(TestAufgabe::isFreitextAufgabe)
                .findFirst()
                .orElse(false);
    }
    public boolean isMCAufgabe(int aufgabenId) {
        return !isFreitextAufgabe(aufgabenId);
    }
    public List<String> getAntwortmoeglichkeiten(int aufgabenId) {
        if(isMCAufgabe(id)) {
            return testAufgaben.stream()
                    .filter(aufgabe -> aufgabe.getId() == aufgabenId)
                    .filter(aufgabe -> !aufgabe.isFreitextAufgabe())
                    .map(aufgabe -> ((McAufgabe) aufgabe).getAntwortMoeglichkeiten())
                    .findFirst()
                    .orElse(List.of());
        }
        return List.of();
    }
    public Integer getFirstAufgabe() {
        return testAufgaben.get(0).getId();
    }
    public Integer getNextAufgabe(int aufgabeId) {
        if(testAufgaben.size() == 0) {
            return null;
        }
        int nextPos = getIndexOf(aufgabeId) + 1;
        if(nextPos < testAufgaben.size()) {
            return testAufgaben.get(nextPos).getId();
        } else {
            return getFirstAufgabe();
        }
    }
    public Integer getPrevAufgabe(int aufgabeId) {
        if(testAufgaben.size() == 0) {
            return null;
        }
        int prevPos = getIndexOf(aufgabeId) - 1;
        if(prevPos >= 0) {
            return testAufgaben.get(prevPos).getId();
        } else {
            return testAufgaben.get(testAufgaben.size()-1).getId();
        }
    }
    private Integer getIndexOf(int aufgabeId) {
        TestAufgabe aktuelleAufgabe = testAufgaben.stream()
                .filter(aufgabe -> aufgabe.getId() == aufgabeId)
                .findFirst()
                .orElse(null);

        if(aktuelleAufgabe == null) {
            return -1;
        }
        return testAufgaben.indexOf(aktuelleAufgabe);
    }


    // Bearbeitungszeitraum
    public boolean isBearbeitbar(LocalDateTime now) {
        return now.isBefore(testDaten.getEndzeitpunkt()) && now.isAfter(testDaten.getStartzeitpunkt());
    }
    public boolean isAbgelaufen(LocalDateTime now) {
        return now.isAfter(testDaten.getEndzeitpunkt()) && now.isAfter(testDaten.getStartzeitpunkt());
    }


    // TestAntworten
    public void addAntwort(String antwort, int aufgabenId, int studiId) {
        // Wenn schon eine Antwort existiert, wird diese ergänzt bzw. überschrieben
        McAntwort mcAntwort = mcAntworten.stream()
                .filter(aufgabe -> aufgabe.getAufgabeId() == aufgabenId && aufgabe.getStudiId() == studiId)
                .findFirst()
                .orElse(null);

        if(mcAntwort != null) {
            mcAntwort.addAntwort(antwort);
            return;
        }

        FreitextAntwort freitextAntwort = freitextAntworten.stream()
            .filter(aufgabe -> aufgabe.getAufgabeId() == aufgabenId && aufgabe.getStudiId() == studiId)
            .findFirst()
            .orElse(null);

        if(freitextAntwort != null) {
            freitextAntwort.addAntwort(antwort);
            return;
        }

        // Wenn noch keine Antwort existiert wird eine neue erstellt und gespeichert
        if(testAufgaben.get(getIndexOf(aufgabenId)).isFreitextAufgabe())
        {
            FreitextAntwort neueFreitextAntwort = new FreitextAntwort(null, id, aufgabenId, studiId);
            neueFreitextAntwort.addAntwort(antwort);
            freitextAntworten.add(neueFreitextAntwort);
        } else {
            McAntwort neueMcAntwort = new McAntwort(null, id, aufgabenId, studiId);
            neueMcAntwort.addAntwort(antwort);
            mcAntworten.add(neueMcAntwort);
        }
    }

    public String getAntwort(int aufgabeId, int studiId) {
        if(testAufgaben.get(getIndexOf(aufgabeId)).isFreitextAufgabe()) {
            return freitextAntworten.stream()
                    .filter(antwort -> ((FreitextAntwort)antwort).getStudiId() == studiId && antwort.getAufgabeId() == aufgabeId)
                    .map(FreitextAntwort::getAntworten)
                    .findFirst()
                    .orElse("");
        }

        return mcAntworten.stream()
                .filter(antwort -> antwort.getStudiId() == studiId && antwort.getAufgabeId() == aufgabeId)
                .map(McAntwort::getAntworten)
                .findFirst()
                .orElse("");
    }

    // Die braucht die Persistenzschicht
    public void setMcAntworten(List<McAntwort> antworten) {
        this.mcAntworten = antworten;
    }
    public void setFreitextAntworten(List<FreitextAntwort> antworten) {
        this.freitextAntworten = antworten;
    }

    public void setMcAufgaben(List<McAufgabe> mcAufgaben) {
        this.mcAufgaben = mcAufgaben;
    }
    public void setFreitextAufgaben(List<FreitextAufgabe> freitextAufgaben) {
        this.freitextAufgaben = freitextAufgaben;
    }

    // TODO: Das gehört in einen anderen Service aber nicht hier hin
    public boolean testBestanden() {
        return true;
    }

    public TestDaten getTestDaten() {
        return testDaten;
    }
}
