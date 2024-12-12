package exambyte.aggregates.organisatoren;

public interface TestFrage {
    Integer getId();
    Integer getPunkte();
    void setPunkte(Integer punkte);
    String getTitel();
    void setTitel(String titel);
    String getFragestellung();
    void setFragestellung(String fragestellung);
    String getBeschreibung();
    void setBeschreibung(String beschreibung);
    String getErklaerung();
    void setErklaerung(String erklaerung);
    boolean istMcFrage();

}
