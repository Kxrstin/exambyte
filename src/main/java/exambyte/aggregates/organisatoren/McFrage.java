package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Wertobjekt
class McFrage implements TestFrage {
    private final Integer punkte;
    private final String titel;
    private final String fragestellung;
    @Id
    private Integer id;
    private final String erklaerung;
    private List<McAntwortOrga> mcAntwortOrga;
    private Integer testFormular;

    public McFrage(Integer punkte, String titel, String fragestellung, String erklaerung){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.erklaerung = erklaerung;
        mcAntwortOrga = new ArrayList<>();
    }

    @PersistenceCreator
    public McFrage(Integer punkte, String titel, String fragestellung, String erklaerung, List<McAntwortOrga> mcAntwortOrga){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.erklaerung = erklaerung;
        this.mcAntwortOrga = mcAntwortOrga;
    }

    public Integer getTestFormular() {
        return testFormular;
    }
    public void setTestFormular(Integer testFormular) {
        this.testFormular = testFormular;
    }
    public List<McAntwortOrga> getMcAntwortOrga() {
        return mcAntwortOrga;
    }
    public void setMcAntwortOrga(List<McAntwortOrga> mcAntwortOrga) {
        this.mcAntwortOrga = mcAntwortOrga;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
        return true;
    }
}
