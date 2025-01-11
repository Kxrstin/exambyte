package exambyte.service.studenten.loader;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.service.korrektoren.repository.AbgabenRepo;
import exambyte.service.organisatoren.TestFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KorrekturenLoader {
    private final AbgabenRepo abgabenRepo;
    private final TestFormService testFormService;

    @Autowired
    public KorrekturenLoader(AbgabenRepo repo, TestFormService service, TestFormService formService) {
        abgabenRepo = repo;
        testFormService = service;
    }

    public List<Abgabe> getKorrekturenFürTestVonStudi(Integer studiId, Integer studiTest) {
        return abgabenRepo.findAll().stream()
                .filter(abgabe -> abgabe.getPunktzahl() != null)
                .filter(abgabe -> abgabe.getStudiId().equals(studiId) && abgabe.getStudiTest().equals(studiTest))
                .toList();
    }

    public String getErklaerungFürMcAufgabe(Integer aufgabeId) {
        return testFormService.getErklaerungFürMcAufgabe(aufgabeId);
    }
    public String getErklaerungFürFreitextAufgabe(Integer aufgabeId) {
        return testFormService.getErklaerungFürFreitextAufgabe(aufgabeId);
    }

    public String getKorrekturMcAufgabe(Integer aufgabeId, List<String> gewaehlteAntworten) {
        return testFormService.getKorrekturMcAufgabe(aufgabeId, gewaehlteAntworten);
    }

    public double berechneErreichtePunktzahlMcAntwort(Integer aufgabenId, List<String> gewaehlteAntworten) {
        return testFormService.berechneErreichtePunktzahlMcAntwort(aufgabenId, gewaehlteAntworten);
    }
}
