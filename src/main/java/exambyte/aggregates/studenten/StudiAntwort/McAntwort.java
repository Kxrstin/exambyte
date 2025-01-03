package exambyte.aggregates.studenten.StudiAntwort;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class McAntwort implements TestAntwort{
    @Id
    private Integer id;
    private String antworten ="";
    private Integer aufgabeId;
    private Integer studiId;
    private Integer studiTest;

    @Transient
    private List<String> antwortWahl = new ArrayList<>();

    @PersistenceCreator
    public McAntwort(Integer id) {
        this.id = id;
    }

    public McAntwort(Integer id, Integer studiTest, Integer aufgabeId, Integer studiId)
    {
        this.id = id;
        this.studiTest = studiTest;
        this.aufgabeId = aufgabeId;
        this.studiId = studiId;
    }

    public void addAntwort(String antwort) {
        String listeOhneKlammern = antwort.substring(1, antwort.length()-1);
        Collections.addAll(antwortWahl, listeOhneKlammern.split(", "));
        this.antworten = antwortWahl.toString();
    }

    public Integer getId(){
        return id;
    }

    public boolean isFreitextAufgabe() {
        return false;
    }

    public Integer getStudiId() {
        return studiId;
    }

    public void removeAntwort(String antwort) {
        antwortWahl.remove(antwort);
        antworten = antwortWahl.toString();
    }
    public String getAntworten() {
        return antworten;
    }

    @Override
    public void removeAntwort() {

    }

    public void setAufgabeId(int aufgabeId) { this.aufgabeId = aufgabeId;}
    public Integer getAufgabeId() { return aufgabeId; }
}
