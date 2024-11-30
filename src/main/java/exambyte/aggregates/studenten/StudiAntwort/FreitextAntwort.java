package exambyte.aggregates.studenten.StudiAntwort;

class FreitextAntwort implements TestAntwort{
    private String antwort;

    public void addAntwort(String antwort) {
        this.antwort = antwort;
    }

    @Override
    public boolean isFreitextAufgabe() {
        return true;
    }
    public void removeAntwort() {
        this.antwort = "";
    }
    public String getAntwort() {
        return antwort;
    }
}
