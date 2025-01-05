package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Wertobjekt
class FreitextFrage implements TestFrage {
    private final Integer punkte;
    private final String titel;
    private final String fragestellung;

    @Id
    private Integer id;
    private final String erklaerung;
    private Integer testFormular;

    public FreitextFrage(Integer punkte, String titel, String fragestellung, String erklaerung) {
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.erklaerung = erklaerung;
    }

    @PersistenceCreator
    public FreitextFrage(Integer punkte, String titel, String fragestellung, String erklaerung, Integer id) {
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.erklaerung = erklaerung;
        this.id = id;
    }

    public List<McAntwortOrga> getMcAntwortOrga() {
        return new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestFormular() {
        return testFormular;
    }
    public void setTestFormular(Integer testFormular) {
        this.testFormular = testFormular;
    }

    public Integer getPunkte() {
        return punkte;
    }

    public String getTitel() {
        return titel;
    }

    public String getFragestellung() {
        return fragestellung;
    }


    public String getErklaerung() {
        return erklaerung;
    }

    public boolean istMcFrage(){
        return false;
    }
}
