package exambyte.aggregates.organisatoren;

import exambyte.annotations.AggregateRoot;

import java.util.ArrayList;
import java.util.List;

@AggregateRoot
public class TestFormular {
    private List<TestFrage> testFragen;

    public TestFormular(List<TestFrage> testFragen){
        this.testFragen = testFragen;
    }

    //Ermöglicht das Ändern der Reihenfolge, falls User unzufrieden ist
    public void tauscheFragen(int firstPos, int secPos) {
        TestFrage testFrage = testFragen.get(firstPos - 1);
        testFragen.set(firstPos, testFragen.get(secPos - 1));
        testFragen.set(secPos, testFrage);
    }

    public void addTestfrage(TestFrage testFrage) {
        testFragen.add(testFrage);
    }
    public List<TestFrage> getTestFragen(){
        return testFragen;
    }

    public void setTestFragen(List<TestFrage> testFragen) {
        this.testFragen = testFragen;
    }
}
