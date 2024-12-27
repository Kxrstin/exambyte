package exambyte.aggregates.organisatoren;

import exambyte.annotations.Wertobjekt;

@Wertobjekt
class McAntwort {
    private String name;
    private boolean antwort;

    public McAntwort(boolean antwort, String name) {
        this.name = name;
        this.antwort = antwort;
    }

    public McAntwort() {
        this(false, "");
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
}
