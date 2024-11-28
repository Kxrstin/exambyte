package exambyte.aggregates.studenten;

import exambyte.annotations.AggregateRoot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AggregateRoot
public final class StudiTest {
    private String titel;
    private LocalDate startzeitpunkt;
    private LocalDate endzeitpunkt;
    private LocalDate ergebniszeitpunkt;
    private int testId;

    public StudiTest(String titel, Integer id, LocalDate startzeitpunkt, LocalDate endzeitpunkt, LocalDate ergebniszeitpunkt) {
        this.endzeitpunkt = endzeitpunkt;
        this.testId = id;
        this.ergebniszeitpunkt = ergebniszeitpunkt;
        this.startzeitpunkt = startzeitpunkt;
        this.titel = titel;
    }

    public int getId() {
        return testId;
    }

    public LocalDate getEndzeitpunkt() {
        return endzeitpunkt;
    }

    public LocalDate getErgebniszeitpunkt() {
        return ergebniszeitpunkt;
    }

    public LocalDate getStartzeitpunkt() {
        return startzeitpunkt;
    }

    public String getTitel() {
        return titel;
    }

    public boolean testBestanden() {
        // TODO Das gehört in einen anderen Service aber nicht hier hin
        return true;
    }

    // TODO: Antwort speichern
}
