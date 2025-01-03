package exambyte.service.studenten;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.service.korrektoren.AbgabenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KorrekturenLoader {
    private final AbgabenRepo abgabenRepo;

    @Autowired
    public KorrekturenLoader(AbgabenRepo abgabenRepo) {
        this.abgabenRepo = abgabenRepo;
    }

    public List<Abgabe> getKorrekturenFürTestVonStudi(Integer studiId, Integer studiTest) {
        return abgabenRepo.findAll().stream()
                .filter(abgabe -> abgabe.getPunktzahl() != null)
                .filter(abgabe -> abgabe.getStudiId().equals(studiId) && abgabe.getStudiTest().equals(studiTest))
                .toList();
    }

    public List<Abgabe> getAllKorrekturenFürStudi(Integer studiId) {
        return abgabenRepo.findAll().stream()
                .filter(abgabe -> abgabe.getPunktzahl() != null)
                .filter(abgabe -> abgabe.getStudiId().equals(studiId))
                .toList();
    }
}
