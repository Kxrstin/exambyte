package exambyte.aggregates.studenten;

import exambyte.annotations.AggregateRoot;

import java.time.LocalDate;

@AggregateRoot //TODO: ÄNDERN!
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

    String getTitel() {
        return titel;
    }
    LocalDate getStartzeitpunkt() {
        return startzeitpunkt;
    }
    LocalDate getEndzeitpunkt() {
        return endzeitpunkt;
    }
    LocalDate getErgebniszeitpunkt() {
        return ergebniszeitpunkt;
    }
    public int getTestId() {
        return testId;
    }
}
