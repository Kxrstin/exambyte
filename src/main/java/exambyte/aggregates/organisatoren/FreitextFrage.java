package exambyte.aggregates.organisatoren;

public class FreitextFrage implements TestFrage {
    private Integer punkte;
    private String titel;
    private String fragestellung;

    public FreitextFrage(Integer punkte, String titel, String fragestellung) {
        this.punkte = punkte;
        this.titel = titel;
        this.fragestellung = fragestellung;
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
}
