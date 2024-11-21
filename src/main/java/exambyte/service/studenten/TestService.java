package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest;
import exambyte.infrastructure.studenten.StudiTestRepoImpl;
import exambyte.persistence.studenten.StudiTestDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final StudiTestRepo testRepo;

    public TestService(StudiTestRepo testRepo) {
        this.testRepo = testRepo;
    }

    public StudiTest getTest(int testId) {
        return testRepo.findTestById(testId);
    }

    public boolean hasTestWithId(int testId) {
       return testRepo.hasTestWithId(testId);
    }

    public List<StudiTest> getTestList() {
        return testRepo.getTestList();
    }
}