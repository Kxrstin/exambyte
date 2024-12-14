package exambyte.aggregates.organisatoren;

import exambyte.annotations.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AggregateRoot
public class TestFormular {
    private String testTitel;
    private Map<Integer, TestFrage> testFragen;
    private int id;

    public TestFormular(String titel, Map<Integer, TestFrage> testFragen){
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

    public void addTestfrage(TestFrage testFrage) {
        testFragen.put(testFrage.getId(), testFrage);
    }

    public void addNewMCFrage() {
        addTestfrage(new MCFrage(0, "", "", "", ""));
    }

    public void addNewFreitextFrage() {
        addTestfrage(new FreitextFrage(0, "", "", "", ""));
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
        testFrage.setPunkte(punkte);
    }

    public void setTestFragenTitelWithId(String titel, int id) {
        TestFrage testFrage = getTestFrageById(id);
        testFrage.setTitel(titel);
    }

    public void setTestFragenFragestellungWithId(String fragestellung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        testFrage.setFragestellung(fragestellung);
    }

    public void setTestFragenBeschreibungWithId(String beschreibung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        testFrage.setBeschreibung(beschreibung);
    }

    public void setTestFragenErklaerungWithId(String erklaerung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        testFrage.setErklaerung(erklaerung);
    }
}
