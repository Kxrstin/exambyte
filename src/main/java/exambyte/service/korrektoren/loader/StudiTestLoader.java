package exambyte.service.korrektoren.loader;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.service.korrektoren.AbgabenService;
import exambyte.service.korrektoren.repository.AbgabenRepo;
import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class StudiTestLoader {
    private final TestFragenService studiService;
    private Integer lastSeen = Integer.MIN_VALUE;
    private final AbgabenService abgabenService;

    @Autowired
    public StudiTestLoader(TestFragenService studiService, AbgabenService abgabenService) {
        this.studiService = studiService;
        this.abgabenService = abgabenService;
    }

    @Scheduled(fixedDelay = 10000)
    public void loadKorrigierbareStudiTests() {
        try {
            List<StudiTest> neueStudiTests = studiService.getAbgelaufeneTests().stream()
                    .filter(test -> test.getId() > lastSeen)
                    .toList();

            for (StudiTest studiTest : neueStudiTests)
            {
                lastSeen = studiTest.getId();

                for (Integer aufgabenId : studiTest.getAufgabenIds().stream().filter(studiTest::isFreitextAufgabe).toList()) {
                    for (Integer studiId : studiTest.getStudiIdsVonAntworten(aufgabenId)) {
                        abgabenService.save(new Abgabe(null,
                                studiTest.getTitel(),
                                studiTest.getAufgabenstellung(aufgabenId) + "#%#" + studiTest.getTitel(aufgabenId),
                                studiId,
                                aufgabenId,
                                studiTest.getAntwort(aufgabenId, studiId),
                                studiTest.getAntwortId(studiId, aufgabenId),
                                studiTest.getPunktzahl(aufgabenId),
                                studiTest.getId()));
                    }
                }
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
