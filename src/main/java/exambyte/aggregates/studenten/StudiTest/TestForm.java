package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;
import java.time.LocalDate;

@Wertobjekt
public class TestForm {
    private final String titel;
    private final LocalDate startzeitpunkt;
    private final LocalDate endzeitpunkt;
    private final LocalDate ergebniszeitpunkt;
    private final int testId;

    public TestForm(String titel, LocalDate start, LocalDate ende, LocalDate ergebnis, int id) {
        this.titel = titel;
        startzeitpunkt = start;
        endzeitpunkt = ende;
        ergebniszeitpunkt = ergebnis;
        testId = id;
    }

    public String getTitel() {
        return titel;
    }
    public LocalDate getStartzeitpunkt() {
        return startzeitpunkt;
    }
    public LocalDate getEndzeitpunkt() {
        return endzeitpunkt;
    }
    public LocalDate getErgebniszeitpunkt() {
        return ergebniszeitpunkt;
    }
    public int getTestId() {
        return testId;
    }
}
