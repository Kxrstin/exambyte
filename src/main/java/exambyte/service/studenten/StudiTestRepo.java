package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest;

import java.util.List;

public interface StudiTestRepo {
    StudiTest loadTestWithId(int id);
    boolean hasTestWithId(int id);
    List<StudiTest> loadTestList();
}
