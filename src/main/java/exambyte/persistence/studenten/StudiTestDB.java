package exambyte.persistence.studenten;

import exambyte.aggregates.studenten.StudiTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudiTestDB {
    Map<Integer, StudiTest> studiTestMap = new HashMap<>();

    // TODO: Durch echte Tests ersetzen, das dient nur als Beispiel
    public StudiTestDB() {
        studiTestMap.put(1, new StudiTest("Algorithmen und Datenstrukturen Test Woche 5", 1, LocalDate.of(2024, 11, 21), LocalDate.of(2024, 11, 30), LocalDate.of(2024, 12, 2)));
        studiTestMap.put(2, new StudiTest("Programmierpraktikum 2 Test Woche 4", 2, LocalDate.of(2024, 11, 20), LocalDate.of(2024, 11, 26), LocalDate.of(2024, 12, 3)));
        studiTestMap.put(3, new StudiTest("Mathematik für Informatik 3 Test Woche 5", 3, LocalDate.of(2024, 11, 19), LocalDate.of(2024, 11, 28), LocalDate.of(2024, 12, 5)));
    }

    public void addTest(StudiTest test) {
        if(!studiTestMap.containsKey(test.getId())) {
            studiTestMap.put(test.getId(), test);
        }
    }

    public StudiTest getTestWithId(int id) {
        if(studiTestMap.containsKey(id)) {
            return studiTestMap.get(id);
        }
        return new StudiTest(null, null, null, null, null);
    }

    public boolean hasTestWithId(int id) {
        return studiTestMap.containsKey(id);
    }

    public List<StudiTest> getTestList() {
        List<StudiTest> studiTests = new ArrayList<>();
        for(Map.Entry<Integer, StudiTest> studiTestEntry: studiTestMap.entrySet()) {
            StudiTest test = studiTestEntry.getValue();
            studiTests.add(test);
        }
        return studiTests;
    }
}
