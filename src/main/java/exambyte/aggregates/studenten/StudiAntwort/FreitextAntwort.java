package exambyte.aggregates.studenten.StudiAntwort;

import exambyte.annotations.Wertobjekt;

@Wertobjekt
public class FreitextAntwort implements TestAntwort{
    private final Integer id;
    private final String antworten;
    private final Integer studiTest;
    private final Integer aufgabeId;
    private final Integer studiId;

    public FreitextAntwort(Integer id, Integer studiTest, Integer aufgabeId, Integer studiId) {
        this.id = id;
        this.studiTest = studiTest;
        this.aufgabeId = aufgabeId;
        this.studiId = studiId;
        this.antworten = "";
    }

    public FreitextAntwort(Integer id, Integer studiTest, Integer aufgabeId, Integer studiId, String antworten) {
        this.id = id;
        this.studiTest = studiTest;
        this.aufgabeId = aufgabeId;
        this.studiId = studiId;
        this.antworten = antworten;
    }

    public FreitextAntwort addAntwort(String antwort) {
        return new FreitextAntwort(id, studiTest, aufgabeId, studiId, antwort);
    }
    public Integer getStudiId() {
        return studiId;
    }
    public boolean isFreitextAufgabe() {
        return true;
    }
    public String getAntworten() {
        return antworten;
    }
    public int getAufgabeId() { return aufgabeId; }
    public Integer getId() { return id; }
    public Integer getStudiTest() {
        return studiTest;
    }
}
