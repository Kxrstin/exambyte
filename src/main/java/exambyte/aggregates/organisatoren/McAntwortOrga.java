package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;
import org.springframework.data.annotation.Id;

@Wertobjekt
class McAntwortOrga {
    private String name;
    private boolean antwort;

    @Id
    private Integer id;
    private Integer mcFrage;
    private Integer testFormular;
    public McAntwortOrga(boolean antwort, String name) {
        this.name = name;
        this.antwort = antwort;
    }

    public McAntwortOrga() {
        this(false, "");
    }

    public Integer getMcFrage() {
        return mcFrage;
    }
    public void setMcFrage(Integer mcFrage) {
        this.mcFrage = mcFrage;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAntwort() {
        return antwort;
    }

    public void setAntwort(boolean antwort) {
        this.antwort = antwort;
    }

    public void setTestFormular(Integer testFormular) {
        this.testFormular = testFormular;
    }

    public Integer getTestFormular() {
        return testFormular;
    }
    public void setId(Integer id){
        this.id = id;
    }
}
