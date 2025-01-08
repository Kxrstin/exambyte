package exambyte.controller.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestDaten;
import exambyte.service.studenten.TestFragenService;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.Mockito.when;

/** **/

public class TestServiceBuilder {
    private final TestFragenService testService;

    public TestServiceBuilder(TestFragenService testService) {
        this.testService = testService;
    }

    public TestServiceBuilder withNextAndPrevAufgabe() {
        when(testService.getTest(anyInt())).thenReturn(new StudiTest(0,
                new TestDaten(null, null, null, null, 0)));
        return this;
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

    public TestServiceBuilder isMcAufgabe(boolean mcAufgabe) {
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

    public TestServiceBuilder withErgebnisZeitpunkt(LocalDateTime time) {
        when(testService.getErgebnisZeitpunkt(anyInt())).thenReturn(time);
        return this;
    }
}
