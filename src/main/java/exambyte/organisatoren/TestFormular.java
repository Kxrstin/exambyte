package exambyte.organisatoren;

import java.util.ArrayList;

public record TestFormular(ArrayList<TestFrage> testFragen) {

    //Ermöglicht das Ändern der Reihenfolge, falls User unzufrieden ist
    public void fragenTauschen(int firstPos, int secPos) {
        TestFrage testFrage = testFragen.get(firstPos);
        testFragen.set(firstPos, testFragen.get(secPos));
        testFragen.set(secPos, testFrage);
    }
}
