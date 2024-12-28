package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;
import java.time.LocalDateTime;

@Wertobjekt
public class TestDaten {
    private final String titel;
    private final LocalDateTime startzeitpunkt;
    private final LocalDateTime endzeitpunkt;
    private final LocalDateTime ergebniszeitpunkt;
    private final Integer studiTest;

    public TestDaten(String titel, LocalDateTime startzeitpunkt, LocalDateTime endzeitpunkt, LocalDateTime ergebniszeitpunkt, Integer studiTest) {
        this.titel = titel;
        this.startzeitpunkt = startzeitpunkt;
        this.endzeitpunkt = endzeitpunkt;
        this.ergebniszeitpunkt = ergebniszeitpunkt;
        this.studiTest = studiTest;
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
    public int getStudiTest() {
        return studiTest;
    }
}
