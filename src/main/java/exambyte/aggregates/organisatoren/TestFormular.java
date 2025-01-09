package exambyte.aggregates.organisatoren;

import exambyte.annotations.AggregateRoot;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.*;

@AggregateRoot
public class TestFormular {
    @Transient
    private List<TestFrage> testFragen;
    @Transient
    private Map<Integer, TestFrage> testFragenMap;
    @Id
    private Integer id;
    private String testTitel;
    private LocalDateTime startzeitpunkt;
    private LocalDateTime endzeitpunkt;
    private LocalDateTime ergebniszeitpunkt;
    private final List<McFrage> mcFragen;
    private final List<FreitextFrage> freitextFragen;
    private List<McAntwortOrga> mcAntwortOrga;

    @PersistenceCreator
    public TestFormular(Integer id, String testTitel, List<McFrage> mcFragen, List<FreitextFrage> freitextFragen){
        this.testTitel = testTitel;
        this.mcFragen = mcFragen;
        this.freitextFragen = freitextFragen;
        this.testFragen = getTestFragenFromMcFragenFreitextFragen();
        this.testFragenMap = getTestFragenAsMap();
        this.id = id;
    }

    public TestFormular(String titel, List<McFrage> mcFragen, List<FreitextFrage> freitextFragen) {
        this(null, titel, mcFragen, freitextFragen);
    }

    public TestFormular(Integer id,
                        String testTitel,
                        LocalDateTime startzeitpunkt,
                        LocalDateTime endzeitpunkt,
                        LocalDateTime ergebniszeitpunkt,
                        List<McFrage> mcFragen,
                        List<FreitextFrage> freitextFragen,
                        List<McAntwortOrga> mcAntwortOrga) {
        this.id = id;
        this.testTitel = testTitel;
        this.startzeitpunkt = startzeitpunkt;
        this.endzeitpunkt = endzeitpunkt;
        this.ergebniszeitpunkt = ergebniszeitpunkt;
        this.mcFragen = mcFragen;
        this.freitextFragen = freitextFragen;
        this.mcAntwortOrga = mcAntwortOrga;
        this.testFragen = getTestFragenFromMcFragenFreitextFragen();
        this.testFragenMap = getTestFragenAsMap();
    }

    private List<TestFrage> getTestFragenFromMcFragenFreitextFragen() {
        List<TestFrage> testFragen = new ArrayList<>();
        for(McFrage mcFrage : mcFragen) {
            TestFrage testFrage = mcFrage;
            testFragen.add(testFrage);
        }
        for(FreitextFrage freitextFrage: freitextFragen) {
            TestFrage testFrage = freitextFrage;
            testFragen.add(testFrage);
        }
        return testFragen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private void addTestfrage(TestFrage testFrage) {
        testFragenMap.put(testFrage.getId(), testFrage);
        testFragen = getTestFragenAsList();
    }

    public void addNewMCFrage() {
        McFrage mcFrage = new McFrage(0, "", "", "");
        mcFrage.setId(UUID.randomUUID().hashCode());
        addTestfrage(mcFrage);
    }

    public void addNewFreitextFrage() {
        FreitextFrage frage = new FreitextFrage(0, "", "", "");
        frage.setId(UUID.randomUUID().hashCode());
        addTestfrage(frage);
    }

    public void addMCFrage(int punkte, String titel, String fragestellung, String erklaerung, int id){
        McFrage mcFrage = new McFrage(punkte, titel, fragestellung, erklaerung);
        mcFrage.setId(id);
        testFragenMap.put(id, mcFrage);
        testFragen = getTestFragenAsList();
        mcFragen.add(mcFrage);
    }

    public void addFreitextFrage(int punkte, String titel, String fragestellung, String erklaerung, int id){
        testFragenMap.put(id, new FreitextFrage(punkte, titel, fragestellung, erklaerung, id));
        testFragen = getTestFragenAsList();
        freitextFragen.add(new FreitextFrage(punkte, titel, fragestellung, erklaerung, id));
    }


    public Map<Integer, TestFrage> getTestFragenAsMap() {
        Map<Integer, TestFrage> testFragenMap = new HashMap<>();
        for(TestFrage testFrage : testFragen) {
            testFragenMap.put(testFrage.getId(), testFrage);
        }
        return testFragenMap;
    }

    public List<TestFrage> getTestFragenAsList(){
        return testFragenMap.entrySet().stream()
                .map(entry -> entry.getValue())
                .toList();
    }
    public void setTestFragenMap(Map<Integer, TestFrage> testFragenMap) {
        this.testFragenMap = testFragenMap;
    }
    private TestFrage getTestFrageById(int id) {
        return testFragenMap.get(id);
    }
    public String getTestTitel() {
        return testTitel;
    }
    public void setTestTitel(String testTitel) {
        this.testTitel = testTitel;
    }

    public void setPunkteWithId(int punkte, int id){
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            McFrage mcFrage = new McFrage(punkte, testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getErklaerung());
            mcFrage.setId(UUID.randomUUID().hashCode());
            testFragenMap.put(id, mcFrage);
        } else {
            testFragenMap.put(id, new FreitextFrage(punkte, testFrage.getTitel(), testFrage.getFragestellung(), testFrage.getErklaerung()));
        }
    }

    public void setTitelWithId(String titel, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            McFrage mcFrage = new McFrage(testFrage.getPunkte(), titel, testFrage.getFragestellung(), testFrage.getErklaerung());
            mcFrage.setId(UUID.randomUUID().hashCode());
            testFragenMap.put(id, mcFrage);
        } else {
            testFragenMap.put(id, new FreitextFrage(testFrage.getPunkte(), titel, testFrage.getFragestellung(), testFrage.getErklaerung()));
        }
    }

    public void setFragestellungWithId(String fragestellung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            McFrage mcFrage = new McFrage(testFrage.getPunkte(), testFrage.getTitel(), fragestellung, testFrage.getErklaerung());
            mcFrage.setId(UUID.randomUUID().hashCode());
            testFragenMap.put(id, mcFrage);
        } else {
            testFragenMap.put(id, new FreitextFrage(testFrage.getPunkte(), testFrage.getTitel(), fragestellung, testFrage.getErklaerung()));
        }
    }

    public void setErklaerungWithId(String erklaerung, int id) {
        TestFrage testFrage = getTestFrageById(id);
        if(testFrage.istMcFrage()) {
            McFrage mcFrage = new McFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), erklaerung);
            mcFrage.setId(UUID.randomUUID().hashCode());
            testFragenMap.put(id, mcFrage);
        } else {
            testFragenMap.put(id, new FreitextFrage(testFrage.getPunkte(), testFrage.getTitel(), testFrage.getFragestellung(), erklaerung));
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
        TestFrage testFrage = testFragenMap.get(frageID);
        testFrage.getMcAntwortOrga().add(new McAntwortOrga());
    }

    public void addMcAntwort(McAntwortOrga mcAntwort, int frageID) {
        TestFrage testFrage = testFragenMap.get(frageID);
        testFrage.getMcAntwortOrga().add(mcAntwort);
        mcAntwortOrga.add(mcAntwort);
    }

    public void addMcAntworten(List<Boolean> antworten, List<String> antwortmoeglichkeiten, int frageID) {
        List<McAntwortOrga> mcAntworten = new ArrayList<>();
        for(int i = 0; i < antwortmoeglichkeiten.size(); i++) {
            boolean antwort = antworten.get(i);
            String antwortmoeglichkeit = antwortmoeglichkeiten.get(i);
            McAntwortOrga mcAntwort = new McAntwortOrga(antwort, antwortmoeglichkeit);
            mcAntworten.add(mcAntwort);
        }

        TestFrage tf = testFragenMap.get(frageID);
        McFrage mcFrage = new McFrage(tf.getPunkte(),tf.getTitel(),tf.getFragestellung(),tf.getErklaerung(), mcAntworten);
        mcFrage.setId(tf.getId());
        testFragenMap.put(frageID, mcFrage);
        McFrage mcFrage2 = mcFragen.stream().filter(frage -> frage.getId().equals(frageID) == true).findFirst().orElse(null);
        mcFrage2.setMcAntwortOrga(mcAntworten);
    }

    public void setAllTestFormIdsToNull() {
        this.id = null;
        for(TestFrage mcFrage: mcFragen) {
            mcFrage.setTestFormular(null);
            for(McAntwortOrga mcAntwort: mcFrage.getMcAntwortOrga()) {
                mcAntwort.setMcFrage(mcFrage.getId());
                mcAntwort.setId(null);
            }
        }
        for(TestFrage freitextFrage: freitextFragen) {
            freitextFrage.setTestFormular(null);
        }
    }

    public List<McFrage> getMcFragen() {
        return mcFragen;
    }

    public List<FreitextFrage> getFreitextFragen() {
        return freitextFragen;
    }

    public List<McAntwortOrga> getMcAntworten() {
        if(mcAntwortOrga == null) {
            return new ArrayList<>();
        }
        return mcAntwortOrga;
    }

    public List<String> getAntwortMoeglickeiten(TestFrage mcFrage) {
        if(mcFrage.istMcFrage()) {
            return mcFrage.getMcAntwortOrga().stream()
                    .map(aufgabe -> aufgabe.getName())
                    .toList();
        }
        return List.of();
    }
}
