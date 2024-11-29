package exambyte.controller.studenten;

import exambyte.service.studenten.TestFragenService;

import java.util.List;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class TestServiceBuilder {
    private final TestFragenService testService;

    public TestServiceBuilder(TestFragenService testService) {
        this.testService = testService;
    }

    public TestFragenService build() {
        return testService;
    }

    public TestServiceBuilder withPunktzahl(int punkte) {
        when(testService.getPunktzahl(anyInt(), anyInt())).thenReturn(punkte);
        return this;
    }

    public TestServiceBuilder withAufgabe(String aufgabe) {
        when(testService.getAufgabe(anyInt(), anyInt())).thenReturn(aufgabe);
        return this;
    }

    public TestServiceBuilder isFreitext(boolean freitext) {
        when(testService.isFreitextAufgabe(anyInt(), anyInt())).thenReturn(freitext);
        return this;
    }

    public TestServiceBuilder isMCAufgabe(boolean mcAufgabe) {
        when(testService.isMCAufgabe(anyInt(), anyInt())).thenReturn(mcAufgabe);
        return this;
    }

    public TestServiceBuilder withAnzahlAufgaben(int anzahl) {
        when(testService.getAnzahlAufgaben(anyInt())).thenReturn(anzahl);
        return this;
    }

    public <T> TestServiceBuilder withAntwortMoeglichkeiten(List<String> antworten) {
        when(testService.getAntwortMoeglichkeiten(anyInt(), anyInt())).thenReturn(antworten);
        return this;
    }
}
