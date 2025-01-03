package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Wertobjekt
class McFrage implements TestFrage {
    private final Integer punkte;
    private final String titel;
    private final String fragestellung;
    @Id
    private final Integer id;
    private final String erklaerung;
    private List<McAntwort> antworten;

    public McFrage(Integer punkte, String titel, String fragestellung, String erklaerung){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.erklaerung = erklaerung;
        id = UUID.randomUUID().hashCode();
        antworten = new ArrayList<>();
    }

    public McFrage(Integer punkte, String titel, String fragestellung, String erklaerung, Integer id){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.erklaerung = erklaerung;
        this.id = id;
        antworten = new ArrayList<>();
    }

    public McFrage(Integer punkte, String titel, String fragestellung, String erklaerung, List<McAntwort> antworten, Integer id){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.erklaerung = erklaerung;
        this.id = id;
        this.antworten = antworten;
    }

    public List<McAntwort> getAntworten() {
        return antworten;
    }
    public Integer getId() {
        return id;
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
