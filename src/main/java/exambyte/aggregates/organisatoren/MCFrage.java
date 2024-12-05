package exambyte.aggregates.organisatoren;

import java.util.UUID;

class MCFrage implements TestFrage {
    private Integer punkte;
    private String titel;
    private String fragestellung;
    private String beschreibung;
    private Integer id;
    private String erklaerung;

    public MCFrage(Integer punkte, String titel, String fragestellung, String beschreibung, String erklaerung){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.beschreibung = beschreibung;
        this.erklaerung = erklaerung;
        id = UUID.randomUUID().hashCode();
    }

    public Integer getId() {
        return id;
    }

    public Integer getPunkte() {
        return punkte;
    }

    public void setPunkte(Integer punkte) {
        this.punkte = punkte;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getFragestellung() {
        return fragestellung;
    }

    public void setFragestellung(String fragestellung) {
        this.fragestellung = fragestellung;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getErklaerung() {
        return erklaerung;
    }

    public void setErklaerung(String erklaerung) {
        this.erklaerung = erklaerung;
    }

    public boolean istMcFrage(){
        return true;
    }
}
