package exambyte.service.studenten;

import exambyte.aggregates.studenten.StudiTest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    private final StudiTestRepo testRepo;

    public TestService(StudiTestRepo testRepo) {
        this.testRepo = testRepo;
    }

    public StudiTest getTest(int testId) {
        return testRepo.loadTestWithId(testId);
    }

    public boolean hasTestWithId(int testId) {
       return testRepo.hasTestWithId(testId);
    }

    public List<StudiTest> getTestList() {
        return testRepo.loadTestList();
    }

    public String zulassungsStatus(List<StudiTest> tests) {
        int countBestanden = 0;
        for (StudiTest test : tests) {
            if (test.testBestanden()) {
                countBestanden++;
            }
        }
        if(tests.size() == 14 && countBestanden < 14) {
            return "zulassungNichtErreicht";
        } else if(tests.size() == 14 && countBestanden == 14){
            return "zulassungErreicht";
        }
        if (countBestanden == tests.size()) {
            return "guterKurs";
        } else if (countBestanden == tests.size() - 1) {
            return "vorsicht";
        } else if (countBestanden == tests.size() - 2) {
            return "vorsichtiger";
        }
        return "fail";
    }

    // TODO Antwort speichern
    // public void addTestAntwortWithId(Integer id, String antwort)
    // StudiTest test = testRepo.loadTestWithId(...);
    // test.addAntwortZuId(id, antwort);
    // testRepo.safeTestWithId(id);
}