package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest;
import exambyte.infrastructure.studenten.StudiTestRepoImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class TestService {
    private StudiTestRepo testRepo;

    public TestService() {
        testRepo = new StudiTestRepoImpl();
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