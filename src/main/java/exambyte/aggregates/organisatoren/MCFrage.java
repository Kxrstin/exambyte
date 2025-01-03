package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Wertobjekt
class MCFrage implements TestFrage {
    @Min(value = 1, message = "Darf nicht negativ sein") private final Integer punkte;
    @NotBlank(message = "Darf nicht leer sein") private final String titel;
    @NotBlank(message = "Darf nicht leer sein") private final String fragestellung;
    @NotBlank(message = "Darf nicht leer sein") private final String beschreibung;
    private final Integer id;
    @NotBlank(message = "Darf nicht leer sein") private final String erklaerung;
    private List<McAntwort> antworten;

    public MCFrage(Integer punkte, String titel, String fragestellung, String beschreibung, String erklaerung){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.beschreibung = beschreibung;
        this.erklaerung = erklaerung;
        id = UUID.randomUUID().hashCode();
        antworten = new ArrayList<>();
    }

    public MCFrage(Integer punkte, String titel, String fragestellung, String beschreibung, String erklaerung, int id){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.beschreibung = beschreibung;
        this.erklaerung = erklaerung;
        this.id = id;
        antworten = new ArrayList<>();
    }

    public MCFrage(Integer punkte, String titel, String fragestellung, String beschreibung, String erklaerung, List<McAntwort> antworten, int id){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.beschreibung = beschreibung;
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

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getErklaerung() {
        return erklaerung;
    }

    public boolean istMcFrage(){
        return true;
    }
}
