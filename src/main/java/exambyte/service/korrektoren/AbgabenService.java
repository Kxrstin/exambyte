package exambyte.service.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;
import exambyte.service.korrektoren.repository.AbgabenRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbgabenService {
    private final AbgabenRepo abgabenRepo;

    public AbgabenService(AbgabenRepo abgabenRepo) {
        this.abgabenRepo = abgabenRepo;
    }

    public List<String> getTestnamen() {
        List<String> testnamen = new ArrayList<>();
        for(Abgabe abgabe: abgabenRepo.findAll()) {
            if(!testnamen.contains(abgabe.getStudiTestTitel())) {
                if(abgabe.getFeedback() == null && abgabe.getPunktzahl() == null) {
                    testnamen.add(abgabe.getStudiTestTitel());
                }
            }
        }
        return testnamen;
    }
    public List<String> getAufgabenTitelVonTest(String testname) {
        List<String> aufgaben = new ArrayList<>();
        List<Abgabe> abgaben = abgabenRepo.findAll().stream()
                .filter(abgabe -> abgabe.getStudiTestTitel().equals(testname))
                .filter(abgabe -> abgabe.getFeedback() == null && abgabe.getPunktzahl() == null)
                .toList();
        for(Abgabe abgabe: abgaben) {
            if(!aufgaben.contains(abgabe.getAufgabenTitel())) {
                aufgaben.add(abgabe.getAufgabenTitel());
            }
        }
        return aufgaben;
    }

    public String getTestname(int id){
        if(abgabenRepo.findById(id) != null) {
            return abgabenRepo.findById(id).getStudiTestTitel();
        }
        return "";
    }

    public String getAufgabe(int id){
        return abgabenRepo.findById(id).getAufgabe();
    }


    public List<Abgabe> getAbgaben() {
        return abgabenRepo.findAll();
    }

    public List<Integer> getAbgabenIds(String testname, String aufgabe) {
        return abgabenRepo.findAll().stream()
                .filter(abgabe -> abgabe.getStudiTestTitel().equals(testname))
                .filter(abgabe -> abgabe.getAufgabenTitel().equals(aufgabe))
                .filter(abgabe -> abgabe.getFeedback() == null && abgabe.getPunktzahl() == null)
                .map(Abgabe::getId)
                .toList();
    }

    public boolean containsAbgabe(int abgabeId) {
        return abgabenRepo.existsById(abgabeId);
    }

    @Transactional
    public void addKorrektur(int punktzahl, String feedback, int id) {
        Abgabe abgabe = abgabenRepo.findByIdWithLock(id);
        abgabe.setPunktzahl(punktzahl);
        abgabe.setFeedback(feedback);
        abgabenRepo.save(abgabe);
    }

    public Abgabe getAbgabe(int id) {
        return abgabenRepo.findById(id);
    }
    public void save(Abgabe abgabe) {
        abgabenRepo.save(abgabe);
    }
}
