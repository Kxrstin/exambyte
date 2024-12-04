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

    public List<String> getTestnamen() {
        List<String> testnamen = new ArrayList<>();
        for(Abgabe abgabe: abgabenRepo.loadAbgaben()) {
            if(!testnamen.contains(abgabe.getTestname())) {
                if(abgabe.getFeedback() == null && abgabe.getPunktzahl() == null) {
                    testnamen.add(abgabe.getTestname());
                }
            }
        }
        return testnamen;
    }
    public List<String> getAufgabenVonTest(String testname) {
        List<String> aufgaben = new ArrayList<>();
        List<Abgabe> abgaben = abgabenRepo.loadAbgaben().stream()
                .filter(abgabe -> abgabe.getTestname().equals(testname))
                .filter(abgabe -> abgabe.getFeedback() == null && abgabe.getPunktzahl() == null)
                .toList();
        for(Abgabe abgabe: abgaben) {
            if(!aufgaben.contains(abgabe.getAufgabe())) {
                aufgaben.add(abgabe.getAufgabe());
            }
        }
        return aufgaben;
    }

    public String getTestname(int id){
        return abgabenRepo.loadAbgabeWithId(id).getTestname();
    }

    public String getAufgabe(int id){
        return abgabenRepo.loadAbgabeWithId(id).getAufgabe();
    }


    public List<Abgabe> getAbgaben() {
        return abgabenRepo.loadAbgaben();
    }

    public List<Integer> getAbgabenMitTestnameAufgabe(String testname, String aufgabe) {
        return abgabenRepo.loadAbgaben().stream()
                .filter(abgabe -> abgabe.getTestname().equals(testname))
                .filter(abgabe -> abgabe.getAufgabe().equals(aufgabe))
                .filter(abgabe -> abgabe.getFeedback() == null && abgabe.getPunktzahl() == null)
                .map(Abgabe::getId)
                .toList();
    }

    public boolean containsAbgabe(int abgabeId) {
        return abgabenRepo.hasAbgabeWithId(abgabeId);
    }

    public void addKorrektur(int punktzahl, String feedback, int id) {
        Abgabe abgabe = abgabenRepo.loadAbgabeWithId(id);
        abgabe.setPunktzahl(punktzahl);
        abgabe.setFeedback(feedback);
        abgabenRepo.saveAbgabe(abgabe);
    }

    public Abgabe getAbgabe(int id) {
        return abgabenRepo.loadAbgabeWithId(id);
    }
}
