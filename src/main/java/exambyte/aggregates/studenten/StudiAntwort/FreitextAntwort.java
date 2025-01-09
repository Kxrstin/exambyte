package exambyte.aggregates.studenten.StudiAntwort;

public class FreitextAntwort implements TestAntwort{
    private final Integer id;
    // TODO final
    private String antworten;
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

    public void addAntwort(String antwort) {
        this.antworten = antwort;
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
    public Integer getAufgabeId() { return aufgabeId; }
    public Integer getId() { return id; }
    public Integer getStudiTest() {
        return studiTest;
    }
}
