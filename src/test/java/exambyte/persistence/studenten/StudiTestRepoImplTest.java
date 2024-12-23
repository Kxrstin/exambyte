package exambyte.persistence.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestDaten;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
// TODO: An Änderung mit CrudRepository anpassen
public class StudiTestRepoImplTest {
    @Test
    @DisplayName("Der StudiTest mit ID = 1627 kann gespeichert und geladen werden")
    void test_01(){
        StudiTestRepoImpl repoImpl = new StudiTestRepoImpl();
        StudiTest studiTest = new StudiTest(new TestDaten("Titel", null, null, null, 1627), null, null);
        repoImpl.saveTest(studiTest);
        assertThat(repoImpl.loadTestWithId(1627)).isEqualTo(studiTest);
    }

    @Test
    @DisplayName("Der StudiTest mit ID = 1627 kann gespeichert werden und die hasTestWithId Methode funktioniert.")
    void test_02(){
        StudiTestRepoImpl repoImpl = new StudiTestRepoImpl();
        StudiTest studiTest = new StudiTest(new TestDaten("Titel", null, null, null, 1627), null, null);
        repoImpl.saveTest(studiTest);
        assertThat(repoImpl.hasTestWithId(1627)).isTrue();
    }

    @Test
    @DisplayName("Die loadTestList beinhaltet die gespeicherten Tests.")
    void test_03(){
        StudiTestRepoImpl repoImpl = new StudiTestRepoImpl();
        StudiTest studiTest = new StudiTest(new TestDaten("Titel", null, null, null, 1627), null, null);
        repoImpl.saveTest(studiTest);
        assertThat(repoImpl.loadTestList()).contains(studiTest);
    }
}
