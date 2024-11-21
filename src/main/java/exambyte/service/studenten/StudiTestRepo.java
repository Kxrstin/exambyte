package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest;
import org.springframework.stereotype.Component;

import java.util.List;

public interface StudiTestRepo {
    StudiTest findTestById(int id);
    boolean hasTestWithId(int id);
    List<StudiTest> getTestList();
}
