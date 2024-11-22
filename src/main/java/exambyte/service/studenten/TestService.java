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

    public String zulassungsStatus(List<StudiTest> tests) {
        int countBestanden = 0;
        for (StudiTest test : tests) {
            if (test.testBestanden()) {
                countBestanden++;
            }
        }
        if (countBestanden == tests.size()) {
            return "Guter Kurs, du hast bislang alle Tests bestanden!";
        } else if (countBestanden == tests.size() - 1) {
            return "Vorsicht! Du hast einen Test bislang nicht bestanden!";
        } else if (countBestanden == tests.size() - 2) {
            return "Wenn du noch einen weiteren Test nicht bestehst, kannst du die Zulassung nicht mehr erreichen!";
        }
        return "Zulassungsstatus nicht erreicht! Du hast mehr als 2 Tests nicht bestanden!";
    }
}