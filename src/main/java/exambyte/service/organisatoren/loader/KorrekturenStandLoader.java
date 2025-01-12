package exambyte.service.organisatoren.loader;
import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.aggregates.studenten.StudiAntwort.FreitextAntwort;
import exambyte.aggregates.studenten.StudiAntwort.McAntwort;
import exambyte.aggregates.studenten.StudiTest.FreitextAufgabe;
import exambyte.aggregates.studenten.StudiTest.McAufgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.service.korrektoren.AbgabenService;
import exambyte.service.organisatoren.TestFormService;
import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class KorrekturenStandLoader {
    private final AbgabenService abgabenService;
    private final TestFragenService testService;

    @Autowired
    public KorrekturenStandLoader(AbgabenService abgabenService, TestFragenService testService) {
        this.abgabenService = abgabenService;
        this.testService = testService;
    }

    public List<String> loadTestAufgaben(String testname) {
        return abgabenService.getAufgabenTitelVonTest(testname);
    }

    public List<Integer> loadTestIdsKorrigiert() {
        return testService.getAbgelaufeneTests().stream()
                .filter(test -> test.getErgebnisZeitpunkt().isBefore(LocalDateTime.now()))
                .map(StudiTest::getId)
                .toList();
    }

    public List<Integer> loadTestIdsNichtKorrigiert() {
        return abgabenService.getAbgaben().stream()
                .map(Abgabe::getStudiTest)
                .distinct()
                .toList();
    }


    public double loadTestErgebnisse(Integer testId) {
        List<Integer> studiIds = new ArrayList<>();
        List<Integer> mcAbgabenStudiIds = getAbgelaufeneMcAntworten(testId).stream()
                .map(McAntwort::getStudiId)
                .toList();
        List<Integer> freitextAbgabenStudiIds = getAbgelaufeneFreitextAntworten(testId).stream()
                .map(FreitextAntwort::getStudiId)
                .toList();

        for(int i = 0; i < mcAbgabenStudiIds.size(); i++) {
            if(!studiIds.contains(mcAbgabenStudiIds.get(i))) {
                studiIds.add(mcAbgabenStudiIds.get(i));
            }
        }
        for(int i = 0; i < freitextAbgabenStudiIds.size(); i++) {
            if(!studiIds.contains(freitextAbgabenStudiIds.get(i))) {
                studiIds.add(freitextAbgabenStudiIds.get(i));
            }
        }

        double sumMaxPunkte = 0;
        double sumErreichtePunkte = 0;
        for (Integer studiId : studiIds) {
            sumMaxPunkte += testService.getMaxErreichbarePunkte(testId);
            sumErreichtePunkte += testService.getErreichtePunkte(studiId, testId);
        }
        if (sumMaxPunkte > 0) {
            return Math.round((sumErreichtePunkte / sumMaxPunkte) * 10000.0) / 100.0;
        }
        return 0.0;
    }

    public String loadTestName(Integer id) {
        return testService.getTest(id).getTitel();
    }

    private List<McAntwort> getAbgelaufeneMcAntworten(Integer testId) {
        return testService.getTest(testId).getMcAntworten();
    }

    private List<FreitextAntwort> getAbgelaufeneFreitextAntworten(Integer testId) {
        return testService.getTest(testId).getFreitextAntworten();
    }
}
