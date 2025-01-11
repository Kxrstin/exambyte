package exambyte.persistence.studenten.repository;

import exambyte.aggregates.studenten.StudiAntwort.FreitextAntwort;
import exambyte.aggregates.studenten.StudiAntwort.McAntwort;
import exambyte.aggregates.studenten.StudiTest.*;
import exambyte.persistence.studenten.data.*;
import exambyte.service.studenten.repository.StudiTestRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class StudiTestRepositoryImpl implements StudiTestRepository {
    private final StudiTestDataRepository repo;

    public StudiTestRepositoryImpl(StudiTestDataRepository repo) {
        this.repo = repo;
    }

    @Override
    public StudiTest findById(int id) {
        StudiTestDto test = repo.findById(id);
        return toStudiTest(test);
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public StudiTest save(StudiTest test) {
        if(!repo.existsById(test.getId())) {
            repo.insertNewTest(test.getId(), toTestDatenDto(test.getTestDaten()));
        }
        StudiTestDto studiTestDto = toStudiTestDto(test);
        StudiTestDto saved = repo.save(studiTestDto);
        return toStudiTest(saved);
    }

    @Override
    public List<StudiTest> findAll() {
        return repo.findAll().stream()
                .map(this::toStudiTest)
                .toList();
    }

    private StudiTestDto toStudiTestDto(StudiTest studiTest) {
        List<McAntwort> mcAntworten = new ArrayList<>(studiTest.getMcAntworten());

        List<FreitextAntwort> freitextAntworten = new ArrayList<>(studiTest.getFreitextAntworten());
        return new StudiTestDto(
                studiTest.getId(),
                toTestDatenDto(studiTest.getTestDaten()),
                studiTest.getFreitextAufgaben().stream()
                        .map(this::toFreitextAufgabeDto)
                        .toList(),
                studiTest.getMcAufgaben().stream()
                        .map(this::toMcAufgabeDto)
                        .toList(),
                mcAntworten.stream()
                        .map(this::toMcAntwortDto)
                        .toList(),
                freitextAntworten.stream()
                        .map(this::toFreitextAntwortDto)
                        .toList()
        );
    }

    private FreitextAntwortDto toFreitextAntwortDto(FreitextAntwort freitextAntwort) {
        return new FreitextAntwortDto(freitextAntwort.getId(),
                freitextAntwort.getAntworten(),
                freitextAntwort.getStudiTest(),
                freitextAntwort.getAufgabeId(),
                freitextAntwort.getStudiId()
        );
    }

    private McAntwortDto toMcAntwortDto(McAntwort mcAntwort) {
        return new McAntwortDto(mcAntwort.getId(),
                mcAntwort.getAntworten(),
                mcAntwort.getAufgabeId(),
                mcAntwort.getStudiId(),
                mcAntwort.getStudiTest());
    }

    private McAufgabeDto toMcAufgabeDto(McAufgabe mcAufgabe) {
        return new McAufgabeDto(mcAufgabe.getId(),
                mcAufgabe.getAufgabe(),
                mcAufgabe.getAntwortMoeglichkeiten().stream()
                        .map(this::toAntwortMoeglichkeitenDto)
                        .toList(),
                mcAufgabe.getPunktzahl(),
                mcAufgabe.getStudiTest()
        );
    }

    private AntwortMoeglichkeitenDto toAntwortMoeglichkeitenDto(AntwortMoeglichkeiten antworten) {
        return new AntwortMoeglichkeitenDto(antworten.getAntwort(),
                antworten.getMcAufgabe());
    }

    private FreitextAufgabeDto toFreitextAufgabeDto(FreitextAufgabe freitextAufgabe) {
        return new FreitextAufgabeDto(freitextAufgabe.getId(),
                freitextAufgabe.getAufgabe(),
                freitextAufgabe.getPunktzahl(),
                freitextAufgabe.getStudiTest());
    }

    private TestDatenDto toTestDatenDto(TestDaten testDaten) {
        return new TestDatenDto(testDaten.getTitel(),
                testDaten.getStartzeitpunkt(),
                testDaten.getEndzeitpunkt(),
                testDaten.getErgebniszeitpunkt(),
                testDaten.getStudiTest());
    }

    private StudiTest toStudiTest(StudiTestDto studiTestDto) {
        StudiTest neuerTest = new StudiTest(
                studiTestDto.id(),
                toTestDaten(studiTestDto.testDaten()),
                studiTestDto.mcAufgaben().stream().map(this::toMcAufgabe).toList(),
                studiTestDto.freitextAufgaben().stream().map(this::toFreitextAufgabe).toList()
        );
        neuerTest.setFreitextAntworten(new ArrayList<>(studiTestDto
                .freitextAntworten()
                .stream()
                .map(this::toFreitextAntwort)
                .toList()));
        neuerTest.setMcAntworten(new ArrayList<>(studiTestDto
                .mcAntworten()
                .stream()
                .map(this::toMcAntwort)
                .toList()));
        return neuerTest;
    }

    private McAntwort toMcAntwort(McAntwortDto mcAntwortDto) {
        McAntwort antwort = new McAntwort(mcAntwortDto.id(),
                mcAntwortDto.studiTest(),
                mcAntwortDto.aufgabeId(),
                mcAntwortDto.studiId());
        antwort.addAntwort(mcAntwortDto.antworten());
        return antwort;
    }

    private FreitextAntwort toFreitextAntwort(FreitextAntwortDto freitextAntwortDto) {
        FreitextAntwort antwort = new FreitextAntwort(freitextAntwortDto.id(),
                freitextAntwortDto.studiTest(),
                freitextAntwortDto.aufgabeId(),
                freitextAntwortDto.studiId(),
                freitextAntwortDto.antworten()
        );
        return antwort;
    }

    private McAufgabe toMcAufgabe(McAufgabeDto mcAufgabe) {
        return new McAufgabe(mcAufgabe.aufgabe(),
                mcAufgabe.antwortMoeglichkeitenDtos()
                        .stream()
                        .map(this::toAntwortMoeglichkeiten)
                        .toList(),
                mcAufgabe.punktzahl(),
                mcAufgabe.id()
        );
    }

    private FreitextAufgabe toFreitextAufgabe(FreitextAufgabeDto freitextAufgabe) {
        return new FreitextAufgabe(freitextAufgabe.aufgabe(),
                freitextAufgabe.punktzahl(),
                freitextAufgabe.id(),
                freitextAufgabe.studiTest()
        );
    }

    private AntwortMoeglichkeiten toAntwortMoeglichkeiten(AntwortMoeglichkeitenDto antwortMoeglichkeiten) {
        return new AntwortMoeglichkeiten(antwortMoeglichkeiten.antwort(),
                antwortMoeglichkeiten.mcAufgabe());
    }

    private TestDaten toTestDaten(TestDatenDto testDatenDto) {
        return new TestDaten(
                testDatenDto.titel(),
                testDatenDto.startzeitpunkt(),
                testDatenDto.endzeitpunkt(),
                testDatenDto.ergebniszeitpunkt(),
                testDatenDto.studiTest()
        );
    }
}
