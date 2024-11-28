package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;

import java.util.List;


public interface StudiTestRepo {
    StudiTest loadTestWithId(int id);
    boolean hasTestWithId(int id);
    List<StudiTest> loadTestList();
    void saveTest(StudiTest test);
}
