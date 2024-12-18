package exambyte.aggregates.organisatoren;

import exambyte.annotations.AggregateRoot;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AggregateRoot
public class TestFormular {
    private String testTitel;

    //TODO: Testfragen werden nach ID automatisch sortiert und sind beim Hinzufügen nie an der richtigen Stelle
    private Map<Integer, TestFrage> testFragen;
    private int id;

    private LocalDateTime startzeitpunkt;
    private LocalDateTime endzeitpunkt;
    private LocalDateTime ergebniszeitpunkt;

    public TestFormular(String titel, Map<Integer, TestFrage> testFragen){
        testTitel = titel;
        this.testFragen = testFragen;
        id = UUID.randomUUID().hashCode();
    }

    //Ermöglicht das Ändern der Reihenfolge, falls User unzufrieden ist
//    public void tauscheFragen(int firstPos, int secPos) {
//        TestFrage testFrage = testFragen.get(firstPos - 1);
//        testFragen.set(firstPos, testFragen.get(secPos - 1));
//        testFragen.set(secPos, testFrage);
//    }

    public int getId() {
        return id;
    }

    private void addTestfrage(TestFrage testFrage) {
        testFragen.put(testFrage.getId(), testFrage);
    }

    public void addNewMCFrage() {
        addTestfrage(new MCFrage(0, "", "", "", ""));
    }

    public void addNewFreitextFrage() {
        addTestfrage(new FreitextFrage(0, "", "", "", ""));
    }

    public void addMCFrage(int punkte, String titel, String fragestellung, String beschreibung, String erklaerung, int id){
        testFragen.put(id, new MCFrage(punkte, titel, fragestellung, beschreibung, erklaerung, id));
    }

    public void addFreitextFrage(int punkte, String titel, String fragestellung, String beschreibung, String erklaerung, int id){
        testFragen.put(id, new FreitextFrage(punkte, titel, fragestellung, beschreibung, erklaerung, id));
    }

    public List<TestFrage> getTestFragen(){
        return testFragen.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();
    }

    public TestFrage getTestFrageById(int id) {
        return testFragen.get(id);
    }

    public void setTestFragen(Map<Integer, TestFrage> testFragen) {
        this.testFragen = testFragen;
    }

    public String getTestTitel() {
        return testTitel;
    }

    public void setTestTitel(String testTitel) {
        this.testTitel = testTitel;
    }

    public void setTestFragenPunkteWithId(int punkte, int id){
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new MCFrage(punkte, testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getBeschreibung(), testFrage.getErklaerung()));
        } else {
            testFragen.put(id, new FreitextFrage(punkte, testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getBeschreibung(), testFrage.getErklaerung()));
        }

    }

    public void setTestFragenTitelWithId(String titel, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new MCFrage(testFrage.getPunkte(), titel, testFrage.getFragestellung(), testFrage.getBeschreibung(), testFrage.getErklaerung()));
        } else {
            testFragen.put(id, new FreitextFrage(testFrage.getPunkte(), titel, testFrage.getFragestellung(), testFrage.getBeschreibung(), testFrage.getErklaerung()));
        }
    }

    public void setTestFragenFragestellungWithId(String fragestellung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new MCFrage(testFrage.getPunkte(), testFrage.getTitel(), fragestellung, testFrage.getBeschreibung(), testFrage.getErklaerung()));
        } else {
            testFragen.put(id, new FreitextFrage(testFrage.getPunkte(), testFrage.getTitel(), fragestellung, testFrage.getBeschreibung(), testFrage.getErklaerung()));
        }
    }

    public void setTestFragenBeschreibungWithId(String beschreibung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new MCFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), beschreibung, testFrage.getErklaerung()));
        } else {
            testFragen.put(id, new FreitextFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), beschreibung, testFrage.getErklaerung()));
        }
    }

    public void setTestFragenErklaerungWithId(String erklaerung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            testFragen.put(id, new MCFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getBeschreibung(), erklaerung));
        } else {
            testFragen.put(id, new FreitextFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getBeschreibung(), erklaerung));
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
}
