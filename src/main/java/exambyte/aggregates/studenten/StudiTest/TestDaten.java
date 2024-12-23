package exambyte.aggregates.studenten.StudiTest;

import exambyte.annotations.Wertobjekt;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Wertobjekt
public class TestDaten {
    private String titel;
    private LocalDateTime startzeitpunkt;
    private LocalDateTime endzeitpunkt;
    private LocalDateTime ergebniszeitpunkt;

    @Id
    @Column(name = "studi_test")
    private Integer studiTest;

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
