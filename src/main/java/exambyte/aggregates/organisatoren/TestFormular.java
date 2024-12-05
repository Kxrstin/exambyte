package exambyte.aggregates.organisatoren;

import exambyte.annotations.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AggregateRoot
public class TestFormular {
    private List<TestFrage> testFragen;
    private int id;

    public TestFormular(List<TestFrage> testFragen){
        this.testFragen = testFragen;
        //id = Integer.parseInt(UUID.randomUUID().toString());
        id = UUID.randomUUID().hashCode();
    }

    //Ermöglicht das Ändern der Reihenfolge, falls User unzufrieden ist
    public void tauscheFragen(int firstPos, int secPos) {
        TestFrage testFrage = testFragen.get(firstPos - 1);
        testFragen.set(firstPos, testFragen.get(secPos - 1));
        testFragen.set(secPos, testFrage);
    }

    public int getId() {
        return id;
    }

    public void addTestfrage(TestFrage testFrage) {
        testFragen.add(testFrage);
    }

    public void addNewMCFrage() {
        addTestfrage(new MCFrage(0, "", "", "", ""));
    }

    public void addNewFreitextFrage() {
        addTestfrage(new FreitextFrage(0, "", "", "", ""));
    }

    public List<TestFrage> getTestFragen(){
        return testFragen;
    }

    public void setTestFragen(List<TestFrage> testFragen) {
        this.testFragen = testFragen;
    }
}
