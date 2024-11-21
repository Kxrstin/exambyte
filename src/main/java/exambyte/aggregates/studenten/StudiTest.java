package exambyte.aggregates.studenten;

import java.time.LocalDate;
public class StudiTest {
    private String titel;
    private LocalDate startzeitpunkt;
    private LocalDate endzeitpunkt;
    private LocalDate ergebniszeitpunkt;
    private int id;

    public int getId() {
        return id;
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
}
