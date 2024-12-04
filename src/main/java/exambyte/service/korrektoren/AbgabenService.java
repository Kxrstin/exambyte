package exambyte.service.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbgabenService {
    private final AbgabenRepo abgabenRepo;

    public AbgabenService(AbgabenRepo abgabenRepo) {
        this.abgabenRepo = abgabenRepo;
    }

    public List<Abgabe> getAbgaben() {
        return abgabenRepo.loadAbgaben();
    }

    public List<String> getTestnamen() {
        List<String> testnamen = new ArrayList<>();
        for(Abgabe abgabe: abgabenRepo.loadAbgaben()) {
            if(!testnamen.contains(abgabe.getTestname())) {
                testnamen.add(abgabe.getTestname());
            }
        }
        return testnamen;
    }

    public String getAufgabe(int id) {
        return abgabenRepo.loadAbgabeWithId(id).getAufgabe();
    }

    public String getStudiAntwort(int id) {
        return abgabenRepo.loadAbgabeWithId(id).getStudiAntwort();
    }

    public Integer getId(int id) {
        return abgabenRepo.loadAbgabeWithId(id).getId();
    }

    public String getTestname(int id) {
        return abgabenRepo.loadAbgabeWithId(id).getTestname();
    }
}
