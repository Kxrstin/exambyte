package exambyte.aggregates.studenten;

import exambyte.annotations.AggregateRoot;

@AggregateRoot //TODO: ÄNDERN!
public interface TestAufgabe {
    String getAufgabe();
    void addAntwort(String antwort);
}
