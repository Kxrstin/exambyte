package exambyte.aggregates.organisatoren;

public interface TestFrage {
    Integer getPunkte();
    void setPunkte(Integer punkte);
    String getTitel();
    void setTitel(String titel);
    String getFragestellung();
    void setFragestellung(String fragestellung);
    String getBeschreibung();
    void setBeschreibung(String beschreibung);
    boolean istMcFrage();
}
