package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class TestService {
    // TODO überarbeiten
    private ArrayList<StudiTest> testListe;

    public TestService() {
        testListe = new ArrayList<StudiTest>();

    }

    public void addTest(StudiTest test) {
        testListe.add(test);
    }

    public StudiTest getTest(int id) {
        return testListe.stream()
                .filter(t -> t.id() == id)
                .findFirst()
                .orElse(null);
    }

    public List<StudiTest> getTests() {
        ArrayList<StudiTest> tests = new ArrayList<>();
        for(StudiTest test: testListe) {
            tests.add(new StudiTest(test.titel(),
                    test.startzeitpunkt(),
                    test.endzeitpunkt(),
                    test.ergebniszeitpunkt(),
                    test.id()));
        }
        return tests;
    }

    public boolean hasTestWithId(int id) {
        if(getTest(id) == null) {
            return false;
        }
        return true;
    }
}