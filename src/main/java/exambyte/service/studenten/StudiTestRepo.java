package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudiTestRepo {
    StudiTest loadTestWithId(int id);
    boolean hasTestWithId(int id);
    List<StudiTest> loadTestList();
}
