package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Wertobjekt
class FreitextFrage implements TestFrage {
    private final Integer punkte;
    private final String titel;
    private final String fragestellung;
    private final String beschreibung;
    private final Integer id;
    private final String erklaerung;

    public FreitextFrage(Integer punkte, String titel, String fragestellung, String beschreibung, String erklaerung) {
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.beschreibung = beschreibung;
        this.erklaerung = erklaerung;
        id = UUID.randomUUID().hashCode();
    }

    public FreitextFrage(Integer punkte, String titel, String fragestellung, String beschreibung, String erklaerung, int id) {
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.beschreibung = beschreibung;
        this.erklaerung = erklaerung;
        this.id = id;
    }

    public List<McAntwort> getAntworten() {
        return new ArrayList<>();
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
        return false;
    }
}
