package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;
import java.time.LocalDateTime;

@Wertobjekt
public class TestForm {
    private final String titel;
    private final LocalDateTime startzeitpunkt;
    private final LocalDateTime endzeitpunkt;
    private final LocalDateTime ergebniszeitpunkt;
    private final int testId;

    public TestForm(String titel, LocalDateTime start, LocalDateTime ende, LocalDateTime ergebnis, int id) {
        this.titel = titel;
        startzeitpunkt = start;
        endzeitpunkt = ende;
        ergebniszeitpunkt = ergebnis;
        testId = id;
    }

    public String getTitel() {
        return titel;
    }
    public LocalDateTime getStartzeitpunkt() {
        return startzeitpunkt;
    }
    public LocalDateTime getEndzeitpunkt() {
        return endzeitpunkt;
    }
    public LocalDateTime getErgebniszeitpunkt() {
        return ergebniszeitpunkt;
    }
    public int getTestId() {
        return testId;
    }
}
