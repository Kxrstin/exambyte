package exambyte.persistence.organisatoren.repository;

import exambyte.aggregates.organisatoren.FreitextFrage;
import exambyte.aggregates.organisatoren.McAntwortOrga;
import exambyte.aggregates.organisatoren.McFrage;
import exambyte.aggregates.organisatoren.TestFormular;
import exambyte.aggregates.studenten.StudiAntwort.McAntwort;
import exambyte.persistence.organisatoren.data.FreitextFrageDto;
import exambyte.persistence.organisatoren.data.McAntwortOrgaDto;
import exambyte.persistence.organisatoren.data.McFrageDto;
import exambyte.persistence.organisatoren.data.TestFormularDto;
import exambyte.persistence.organisatoren.repository.TestFormDataRepo;
import exambyte.service.organisatoren.TestFormRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class TestFormRepoImpl implements TestFormRepository {
    private Map<Integer, TestFormular> testForms = new HashMap<>();
    private TestFormDataRepo testFormDataRepo;

    public TestFormRepoImpl(TestFormDataRepo testFormDataRepo) {
        this.testFormDataRepo = testFormDataRepo;
    }

    public TestFormular save(TestFormular testForm) {
        testFormDataRepo.save(toTestFormularDto(testForm));
        return testForm;
    }

    public TestFormular findById(int id) {
        TestFormularDto testFormDto = testFormDataRepo.findById(id);
        return toTestFormular(testFormDto);
    }

    public List<TestFormular> findAll() {
        return testFormDataRepo.findAll().stream().map(testFormularDto -> toTestFormular(testFormularDto)).toList();
    }

    private TestFormularDto toTestFormularDto(TestFormular testForm) {
        List<McAntwortOrga> mcAntworten = new ArrayList<>(testForm.getMcAntworten());
        return new TestFormularDto(testForm.getId(),
                testForm.getTestTitel(),
                testForm.getStartzeitpunkt(),
                testForm.getEndzeitpunkt(),
                testForm.getErgebniszeitpunkt(),
                testForm.getMcFragen().stream().map(mcFrage -> toMcFrageDto(mcFrage)).toList(),
                testForm.getFreitextFragen().stream().map(freitextFrage -> toFreitextFrageDto(freitextFrage)).toList(),
                mcAntworten.stream().map(mcAntwort -> toMcAntwortOrgaDto(mcAntwort)).toList());
    }

    private McAntwortOrgaDto toMcAntwortOrgaDto(McAntwortOrga mcAntwort) {
        return new McAntwortOrgaDto(mcAntwort.getId(),
                mcAntwort.getName(),
                mcAntwort.getAntwort(),
                mcAntwort.getMcFrage(),
                mcAntwort.getTestFormular());
    }

    private McFrageDto toMcFrageDto(McFrage mcFrage) {
        return new McFrageDto(mcFrage.getPunkte(),
                mcFrage.getTitel(),
                mcFrage.getFragestellung(),
                mcFrage.getId(),
                mcFrage.getErklaerung(),
                mcFrage.getMcAntwortOrga().stream().map(mcAntwort ->  toMcAntwortOrgaDto(mcAntwort)).toList(),
                mcFrage.getTestFormular());
    }

    private FreitextFrageDto toFreitextFrageDto(FreitextFrage freitext) {
        return new FreitextFrageDto(freitext.getPunkte(),
                freitext.getTitel(),
                freitext.getFragestellung(),
                freitext.getErklaerung(),
                freitext.getId(),
                freitext.getTestFormular());
    }

    private TestFormular toTestFormular(TestFormularDto testFormularDto) {
        return new TestFormular(testFormularDto.id(),
                testFormularDto.test_titel(),
                testFormularDto.startzeitpunkt(),
                testFormularDto.endzeitpunkt(),
                testFormularDto.ergebniszeitpunkt(),
                testFormularDto.mcFragen().stream().map(mcFrageDto -> toMcFrage(mcFrageDto)).toList(),
                testFormularDto.freitextFragen().stream().map(freitextFrageDto -> toFreitextFrage(freitextFrageDto)).toList(),
                testFormularDto.mcAntwortOrga().stream().map(mcAntwortDto -> toMcAntwort(mcAntwortDto)).toList());
    }

    private McFrage toMcFrage(McFrageDto mcFrageDto) {
        return new McFrage(mcFrageDto.punkte(),
                mcFrageDto.titel(),
                mcFrageDto.fragestellung(),
                mcFrageDto.erklaerung(),
                mcFrageDto.id(),
                mcFrageDto.mcAntwortOrga().stream().map(antwortDto -> toMcAntwort(antwortDto)).toList(),
                mcFrageDto.testFormular());
    }

    private FreitextFrage toFreitextFrage(FreitextFrageDto freitextFrageDto) {
        return new FreitextFrage(freitextFrageDto.punkte(),
                freitextFrageDto.titel(),
                freitextFrageDto.fragestellung(),
                freitextFrageDto.id(),
                freitextFrageDto.erklaerung(),
                freitextFrageDto.testFormular());
    }

    private McAntwortOrga toMcAntwort(McAntwortOrgaDto mcAntwortOrgaDto) {
        return new McAntwortOrga(mcAntwortOrgaDto.id(),
                mcAntwortOrgaDto.name(),
                mcAntwortOrgaDto.antwort(),
                mcAntwortOrgaDto.mcFrage(),
                mcAntwortOrgaDto.testFormular());
    }

    public void updateZeitpunkte(LocalDateTime startzeitpunkt,
                                 LocalDateTime endzeitpunkt,
                                 LocalDateTime ergebniszeitpunkt,
                                 Integer id) {
        testFormDataRepo.updateZeitpunkte(startzeitpunkt, endzeitpunkt, ergebniszeitpunkt, id);
    }
}
