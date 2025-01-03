package exambyte.service.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class StudiTestLoader {
    private final TestFragenService studiService;
    private Integer lastSeen = Integer.MIN_VALUE;
    private final AbgabenRepo abgabenRepo;

    @Autowired
    public StudiTestLoader(TestFragenService studiService, AbgabenRepo abgabenRepo) {
        this.studiService = studiService;
        this.abgabenRepo = abgabenRepo;
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

                for (Integer aufgabenId : studiTest.getAufgabenIds().stream().filter(studiTest::isFreitextAufgabe).toList())
                {
                    for (Integer studiId : studiTest.getStudiIdsVonAntworten(aufgabenId)) {
                        abgabenRepo.save(new Abgabe(null,
                                studiTest.getTitel(),
                                studiTest.getAufgabe(aufgabenId),
                                aufgabenId,
                                studiId,
                                studiTest.getAntwort(aufgabenId, studiId),
                                studiTest.getAntwortId(studiId, aufgabenId),
                                studiTest.getPunktzahl(aufgabenId),
                                studiTest.getId()));
                    }
                }
            }
        } catch(Exception e) {
        }
    }
}
