package exambyte.persistence.studenten;

import exambyte.aggregates.studenten.StudiTest.FreitextAufgabe;
import exambyte.aggregates.studenten.StudiTest.MCAufgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestDaten;
import exambyte.service.studenten.StudiTestRepo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class StudiTestRepoImpl {
    Map<Integer, StudiTest> studiTestMap = new HashMap<>();

    // TODO: Durch echte Tests ersetzen, das dient nur als Beispiel
    public StudiTestRepoImpl() {
//        TestDaten testForm1 = new TestDaten("Programmierpraktikum 2 Test Woche 4", LocalDateTime.of(2024, 12, 1, 14, 0), LocalDateTime.of(2024, 12, 22, 14, 0), LocalDateTime.of(2025, 1, 2, 14, 0), 1);
//        TestDaten testForm2 = new TestDaten("Mathematik für Informatik 3 Test Woche 5", LocalDateTime.of(2024, 12, 3, 12, 0), LocalDateTime.of(2024, 12, 18, 14, 0), LocalDateTime.of(2025, 1, 1, 14, 0), 2);
//        TestDaten testForm3 = new TestDaten("Algorithmen und Datenstrukturen Test Woche 5", LocalDateTime.of(2024, 12, 2, 17, 0), LocalDateTime.of(2024, 12, 04, 17, 40), LocalDateTime.of(2025, 1, 13, 15, 0), 3);
//        studiTestMap.put(testForm1.getTestId(),
//                new StudiTest(testForm1, List.of(new FreitextAufgabe("Nenne pro Argumente der Onion Architektur", 3),
//                new MCAufgabe("Mit welchem Test kann man die Onion Architektur prüfen?", List.of("ArchTest", "WebMvc Test", "AssertJ Test"), 2))));
//
//        studiTestMap.put(testForm2.getTestId(), new StudiTest(testForm2, List.of(new MCAufgabe("Was ist das Ergebnis von 2 + 2", List.of("1", "2", "3", "4", "5"), 2),
//                new FreitextAufgabe("Leite 2x * sin(5x) ab.", 2),
//                new MCAufgabe("Was ist die Stammfunktion von 2?", List.of("2x", "0", "sin(2)"),1))));
//        studiTestMap.put(testForm3.getTestId(), new StudiTest(testForm3, List.of(new FreitextAufgabe("Was ist dynamische Programmierung?", 1), new MCAufgabe("Was ist das Ergebnis von 2 + 2", List.of("1", "2", "3", "4", "5"), 3))));
    }


    public StudiTest loadTestWithId(int id) {
        if(studiTestMap.containsKey(id)) {
            return studiTestMap.get(id);
        }
        return null;
    }


    public boolean hasTestWithId(int id) {
        return studiTestMap.containsKey(id);
    }


    public List<StudiTest> loadTestList() {
        List<StudiTest> studiTests = new ArrayList<>();
        for(Map.Entry<Integer, StudiTest> studiTestEntry: studiTestMap.entrySet()) {
            StudiTest test = studiTestEntry.getValue();
            studiTests.add(test);
        }
        return studiTests;
    }


    public void saveTest(StudiTest test) {
        studiTestMap.put(test.getId(), test);
    }


}
