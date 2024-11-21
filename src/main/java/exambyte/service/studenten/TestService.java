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

    // TODO Implementieren
    public StudiTest getTest(int id) {
        return null;
    }

    public List<StudiTest> getTests() {
        return null;
    }

    public boolean hasTestWithId(int id) {
       return false;
    }
}