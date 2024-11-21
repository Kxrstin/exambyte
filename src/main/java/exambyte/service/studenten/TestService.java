package exambyte.service.studenten;

import exambyte.aggregates.studenten.TestStudenten;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class TestService {
    private ArrayList<TestStudenten> testListe;

    public TestService() {
        testListe = new ArrayList<TestStudenten>();

    }

    public void addTest(TestStudenten test) {
        testListe.add(test);
    }

    public TestStudenten getTest(int id) {
        return testListe.stream()
                .filter(t -> t.id() == id)
                .findFirst()
                .orElse(null);
    }

    public List<TestStudenten> getTests() {
        ArrayList<TestStudenten> tests = new ArrayList<>();
        for(TestStudenten test: testListe) {
            tests.add(new TestStudenten(test.titel(),
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