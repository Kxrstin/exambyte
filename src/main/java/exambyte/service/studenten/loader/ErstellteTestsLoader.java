package exambyte.service.studenten.loader;

import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.aggregates.organisatoren.TestFrage;
import exambyte.aggregates.studenten.StudiTest.*;
import exambyte.service.organisatoren.TestFormService;
import exambyte.service.studenten.TestFragenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ErstellteTestsLoader {
    private final TestFormService testFormService;
    private final TestFragenService testFragenService;

    @Autowired
    public ErstellteTestsLoader(TestFormService testFormService, TestFragenService testFragenService) {
        this.testFormService = testFormService;
        this.testFragenService = testFragenService;
    }

    @Scheduled(fixedDelay = 10000)
    public void loadNeuErstellteTests() {
        try {
            List<TestFormular> neueTestFormulareFuerStudis = testFormService.getTestFormsDBFuerStudis();
            for(TestFormular testForm: neueTestFormulareFuerStudis) {
                if(!testFragenService.hasTestWithId(testForm.getId())) {
                    List<McAufgabe> mcAufgaben = new ArrayList<>();
                    List<FreitextAufgabe> freitextAufgaben = new ArrayList<>();

                    for (TestFrage mcFrage : testForm.getMcFragen()) {
                        List<AntwortMoeglichkeiten> antwortMoeglichkeiten = new ArrayList<>();
                        for (String moeglichkeit : testForm.getAntwortMoeglickeiten(mcFrage)) {
                            antwortMoeglichkeiten.add(new AntwortMoeglichkeiten(moeglichkeit, mcFrage.getId()));
                        }
                        mcAufgaben.add(new McAufgabe(mcFrage.getTitel(), antwortMoeglichkeiten, mcFrage.getPunkte(), mcFrage.getId()));
                    }

                    for (TestFrage freitextFrage : testForm.getFreitextFragen()) {
                        freitextAufgaben.add(new FreitextAufgabe(freitextFrage.getTitel(), freitextFrage.getPunkte(), freitextFrage.getId(), testForm.getId()));
                    }

                    StudiTest neuerStudiTest = new StudiTest(testForm.getId(),
                            new TestDaten(testForm.getTestTitel(), testForm.getStartzeitpunkt(), testForm.getEndzeitpunkt(), testForm.getErgebniszeitpunkt(), testForm.getId()),
                            mcAufgaben,
                            freitextAufgaben);

                    testFragenService.save(neuerStudiTest);
                }
            }

        } catch(Exception e) {
        }
    }
}
