package exambyte.aggregates.organisatoren;

public class MCFrage implements TestFrage {
    private Integer punkte;
    private String titel;
    private String fragestellung;
    private String beschreibung;

    public MCFrage(Integer punkte, String titel, String fragestellung, String beschreibung){
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
        this.beschreibung = beschreibung;
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

    public boolean istMcFrage(){
        return true;
    }
}
