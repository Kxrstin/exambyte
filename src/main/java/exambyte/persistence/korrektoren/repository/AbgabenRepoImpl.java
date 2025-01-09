package exambyte.persistence.korrektoren.repository;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.persistence.korrektoren.data.AbgabeDto;
import exambyte.service.korrektoren.repository.AbgabenRepo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AbgabenRepoImpl implements AbgabenRepo {
    private final AbgabenDataRepo repo;

    public AbgabenRepoImpl(AbgabenDataRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Abgabe> findAll() {
        return repo.findAll().stream()
                .map(this::toAbgabe)
                .toList();
    }

    @Override
    public Abgabe findById(int id) {
        return toAbgabe(repo.findById(id));
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public Abgabe save(Abgabe abgabe) {
        AbgabeDto abgabeDto = toAbgabeDto(abgabe);
        AbgabeDto saved = repo.save(abgabeDto);
        return toAbgabe(saved);
    }

    private AbgabeDto toAbgabeDto(Abgabe abgabe) {
        return new AbgabeDto(abgabe.getId(),
                abgabe.getStudiantwort(),
                abgabe.getAufgabe(),
                abgabe.getAufgabenId(),
                abgabe.getStudiId(),
                abgabe.getStudiTestTitel(),
                abgabe.getFeedback(),
                abgabe.getPunktzahl(),
                abgabe.getMaxPunktzahl(),
                abgabe.getStudiTest(),
                abgabe.getAntwort());
    }

    private Abgabe toAbgabe(AbgabeDto abgabe) {
        Abgabe neueAbgabe = new Abgabe(abgabe.id(),
                abgabe.studiTestTitel(),
                abgabe.aufgabe(),
                abgabe.studiId(),
                abgabe.aufgabenId(),
                abgabe.studiantwort(),
                abgabe.antwort(),
                abgabe.maxPunktzahl(),
                abgabe.studiTest());
        neueAbgabe.setPunktzahl(abgabe.punktzahl());
        neueAbgabe.setFeedback(abgabe.feedback());
        return neueAbgabe;
    }
}
