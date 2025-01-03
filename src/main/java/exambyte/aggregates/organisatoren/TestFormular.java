package exambyte.aggregates.organisatoren;

import exambyte.annotations.AggregateRoot;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.PersistenceCreator;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AggregateRoot
public class TestFormular {
    private List<TestFrage> testFragenList;
    @Transient
    private Map<Integer, TestFrage> testFragen;
    @Id
    private Integer id;
    private String testTitel;
    private LocalDateTime startzeitpunkt;
    private LocalDateTime endzeitpunkt;
    private LocalDateTime ergebniszeitpunkt;

    @PersistenceCreator
    public TestFormular(Integer id, String titel, Map<Integer, TestFrage> testFragen){
        testTitel = titel;
        this.testFragen = testFragen;
        this.id = id;
    }

    public TestFormular(String titel, Map<Integer, TestFrage> testFragen) {
        this(null, titel, testFragen);
    }

    public int getId() {
        return id;
    }

    private void addTestfrage(TestFrage testFrage) {
        testFragen.put(testFrage.getId(), testFrage);
    }

    public void addNewMCFrage() {
        addTestfrage(new McFrage(0, "", "", "", null));
    }

    public void addNewFreitextFrage() {
        addTestfrage(new FreitextFrage(0, "", "", "", null));
    }

    public void addMCFrage(int punkte, String titel, String fragestellung, String erklaerung, int id){
        testFragen.put(id, new McFrage(punkte, titel, fragestellung, erklaerung, id));
    }

    public void addFreitextFrage(int punkte, String titel, String fragestellung, String erklaerung, int id){
        testFragen.put(id, new FreitextFrage(punkte, titel, fragestellung, erklaerung, id));
    }

    public List<TestFrage> getTestFragen(){
        return testFragen.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();
    }

    private TestFrage getTestFrageById(int id) {
        return testFragen.get(id);
    }

    public void setTestFragen(Map<Integer, TestFrage> testFragen) {
        this.testFragen = testFragen;
    }

    public String getTitel() {
        return testTitel;
    }

    public void setTitel(String testTitel) {
        this.testTitel = testTitel;
    }

    public void setPunkteWithId(int punkte, int id){
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new McFrage(punkte, testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getErklaerung()));
        } else {
            testFragen.put(id, new FreitextFrage(punkte, testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getErklaerung()));
        }

    }

    public void setTitelWithId(String titel, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new McFrage(testFrage.getPunkte(), titel, testFrage.getFragestellung(), testFrage.getErklaerung()));
        } else {
            testFragen.put(id, new FreitextFrage(testFrage.getPunkte(), titel, testFrage.getFragestellung(), testFrage.getErklaerung()));
        }
    }

    public void setFragestellungWithId(String fragestellung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new McFrage(testFrage.getPunkte(), testFrage.getTitel(), fragestellung, testFrage.getErklaerung()));
        } else {
            testFragen.put(id, new FreitextFrage(testFrage.getPunkte(), testFrage.getTitel(), fragestellung, testFrage.getErklaerung()));
        }
    }

    public void setErklaerungWithId(String erklaerung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new McFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), erklaerung));
        } else {
            testFragen.put(id, new FreitextFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), erklaerung));
        }
    }

    public LocalDateTime getStartzeitpunkt() {
        return startzeitpunkt;
    }

    public LocalDateTime getEndzeitpunkt() {
        return endzeitpunkt;
    }

    public LocalDateTime getErgebniszeitpunkt() {
        return ergebniszeitpunkt;
    }

    public void setStartzeitpunkt(LocalDateTime startzeitpunkt) {
        this.startzeitpunkt = startzeitpunkt;
    }

    public void setEndzeitpunkt(LocalDateTime endzeitpunkt) {
        this.endzeitpunkt = endzeitpunkt;
    }

    public void setErgebniszeitpunkt(LocalDateTime ergebniszeitpunkt) {
        this.ergebniszeitpunkt = ergebniszeitpunkt;
    }

    public void addNewMcAntwort(int frageID) {
        TestFrage testFrage = testFragen.get(frageID);
        testFrage.getAntworten().add(new McAntwort());
    }

    public void addMcAntwort(McAntwort mcAntwort, int frageID) {
        TestFrage testFrage = testFragen.get(frageID);
        testFrage.getAntworten().add(mcAntwort);
    }

    public void addMcAntworten(List<Boolean> antworten, List<String> antwortmoeglichkeiten, int frageID) {
        List<McAntwort> mcAntworten = new ArrayList<McAntwort>();
        for(int i = 0; i < antwortmoeglichkeiten.size(); i++) {
            boolean antwort = antworten.get(i);
            String antwortmoeglichkeit = antwortmoeglichkeiten.get(i);
            McAntwort mcAntwort = new McAntwort(antwort, antwortmoeglichkeit);
            mcAntworten.add(mcAntwort);
        }

        TestFrage tf = testFragen.get(frageID);
        TestFrage mcFrage = new McFrage(tf.getPunkte(),tf.getTitel(),tf.getFragestellung(),tf.getErklaerung(), mcAntworten, tf.getId());
        testFragen.put(frageID, mcFrage);
    }
}
