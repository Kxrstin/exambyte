package exambyte.aggregates.studenten.StudiAntwort;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;

public class FreitextAntwort implements TestAntwort{
    @Id
    private Integer id;
    private String antworten;
    private Integer studiTest;
    private Integer aufgabeId;
    private Integer studiId;

    @PersistenceCreator
    public FreitextAntwort(Integer id) {
        this.id = id;
    }

    public FreitextAntwort(Integer id, Integer studiTest, Integer aufgabeId, Integer studiId)
    {
        this.id = id;
        this.studiTest = studiTest;
        this.aufgabeId = aufgabeId;
        this.studiId = studiId;
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
    public void removeAntwort() {
        this.antworten = "";
    }
    public String getAntworten() {
        return antworten;
    }
    public Integer getAufgabeId() { return aufgabeId; }
    public int getId() { return id; }
    public void setAufgabeId(int aufgabeId) { this.aufgabeId = aufgabeId;}
}
