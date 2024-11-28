package exambyte.aggregates.studenten;

import exambyte.annotations.AggregateRoot;

import java.time.LocalDate;

@AggregateRoot
public final class StudiTest {
    TestForm testForm;

    public StudiTest(TestForm testForm) {
        this.testForm = testForm;
    }

    public String getTitel() {
        return testForm.getTitel();
    }

    public LocalDate getEndzeitpunkt() {
        return testForm.getEndzeitpunkt();
    }

    public LocalDate getStartzeitpunkt() {
        return testForm.getEndzeitpunkt();
    }
    public LocalDate getErgebnisZeitpunkt() {
        return testForm.getErgebniszeitpunkt();
    }
    public int getId() {
        return testForm.getTestId();
    }

    public boolean testBestanden() {
        // TODO Das gehört in einen anderen Service aber nicht hier hin
        return true;
    }

    // TODO: Antwort speichern
}
