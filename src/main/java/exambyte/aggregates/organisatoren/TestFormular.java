package exambyte.aggregates.organisatoren;

import java.util.ArrayList;
import java.util.List;

public class TestFormular {

    private List<TestFrage> testFragen;

    public TestFormular(List<TestFrage> testFragen){
        this.testFragen = testFragen;
    }

    //Ermöglicht das Ändern der Reihenfolge, falls User unzufrieden ist
    public void fragenTauschen(int firstPos, int secPos) {
        TestFrage testFrage = testFragen.get(firstPos - 1);
        testFragen.set(firstPos, testFragen.get(secPos - 1));
        testFragen.set(secPos, testFrage);
    }
}
