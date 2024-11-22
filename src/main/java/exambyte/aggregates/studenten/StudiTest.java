package exambyte.aggregates.studenten;

import java.time.LocalDate;
public final class StudiTest {
    private String titel;
    private LocalDate startzeitpunkt;
    private LocalDate endzeitpunkt;
    private LocalDate ergebniszeitpunkt;
    private int id;

    public StudiTest(String titel, Integer id, LocalDate startzeitpunkt, LocalDate endzeitpunkt, LocalDate ergebniszeitpunkt) {
        this.endzeitpunkt = endzeitpunkt;
        this.id = id;
        this.ergebniszeitpunkt = ergebniszeitpunkt;
        this.startzeitpunkt = startzeitpunkt;
        this.titel = titel;
    }

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

    public boolean testBestanden() {
        // TODO Implementieren
        return true;
    }
}
