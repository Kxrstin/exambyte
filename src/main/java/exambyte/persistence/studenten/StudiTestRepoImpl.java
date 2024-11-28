package exambyte.persistence.studenten;

import exambyte.aggregates.studenten.StudiTest.FreitextAufgabe;
import exambyte.aggregates.studenten.StudiTest.MCAufgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestForm;
import exambyte.service.studenten.StudiTestRepo;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudiTestRepoImpl implements StudiTestRepo {
    Map<Integer, StudiTest> studiTestMap = new HashMap<>();

    // TODO: Durch echte Tests ersetzen, das dient nur als Beispiel
    public StudiTestRepoImpl() {
        TestForm testForm1 = new TestForm("Programmierpraktikum 2 Test Woche 4", LocalDate.of(2024, 11, 21), LocalDate.of(2024, 11, 30), LocalDate.of(2024, 12, 2), 1);
        TestForm testForm2 = new TestForm("Mathematik für Informatik 3 Test Woche 5", LocalDate.of(2024, 11, 21), LocalDate.of(2024, 11, 30), LocalDate.of(2024, 12, 2), 2);
        TestForm testForm3 = new TestForm("Algorithmen und Datenstrukturen Test Woche 5", LocalDate.of(2024, 11, 21), LocalDate.of(2024, 11, 30), LocalDate.of(2024, 12, 2), 3);
        studiTestMap.put(testForm1.getTestId(), new StudiTest(testForm1, List.of(new FreitextAufgabe("Nenne pro Argumente der Onion Architektur", 3))));
        studiTestMap.put(testForm2.getTestId(), new StudiTest(testForm1, List.of(new MCAufgabe("Was ist das Ergebnis von 2 + 2", List.of("1", "2", "3", "4", "5"), 2))));
        studiTestMap.put(testForm3.getTestId(), new StudiTest(testForm1, List.of(new FreitextAufgabe("Was ist dynamische Programmierung?", 1))));
    }

    @Override
    public StudiTest loadTestWithId(int id) {
        if(studiTestMap.containsKey(id)) {
            return studiTestMap.get(id);
        }
        return new StudiTest(null, null);
    }

    @Override
    public boolean hasTestWithId(int id) {
        return studiTestMap.containsKey(id);
    }

    @Override
    public List<StudiTest> loadTestList() {
        List<StudiTest> studiTests = new ArrayList<>();
        for(Map.Entry<Integer, StudiTest> studiTestEntry: studiTestMap.entrySet()) {
            StudiTest test = studiTestEntry.getValue();
            studiTests.add(test);
        }
        return studiTests;
    }

    @Override
    public void saveTest(StudiTest test) {
        studiTestMap.put(test.getId(), test);
    }


}
