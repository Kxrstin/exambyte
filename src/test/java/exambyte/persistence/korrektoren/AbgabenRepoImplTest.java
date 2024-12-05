package exambyte.persistence.korrektoren;

import exambyte.aggregates.korrektoren.Abgabe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AbgabenRepoImplTest {
    @Test
    @DisplayName("Das Abgabenrepo kann Abgaben speichern und laden")
    void test_01() {
        AbgabenRepoImpl abgabenRepo = new AbgabenRepoImpl();
        abgabenRepo.saveAbgabe(new Abgabe(1, null, null, null, 0));
        assertThat(abgabenRepo.hasAbgabeWithId(1)).isTrue();
    }

    @Test
    @DisplayName("Das loadAbgabeWithId Methode liefert die richtige Abgabe")
    void test_02() {
        Abgabe a = new Abgabe(2, null, null, null, 1);
        AbgabenRepoImpl abgabenRepo = new AbgabenRepoImpl();
        abgabenRepo.saveAbgabe(a);
        assertThat(abgabenRepo.loadAbgabeWithId(2)).isEqualTo(a);
    }

    @Test
    @DisplayName("Das loadAbgaben Methode liefert die richtige Abgabe")
    void test_03() {
        Abgabe a = new Abgabe(2, null, null, null, 1);
        AbgabenRepoImpl abgabenRepo = new AbgabenRepoImpl();
        abgabenRepo.saveAbgabe(a);
        assertThat(abgabenRepo.loadAbgaben().contains(a)).isTrue();
    }
}
