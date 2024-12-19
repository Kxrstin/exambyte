package exambyte.persistence.studenten;

import exambyte.aggregates.studenten.StudiTest.StudiTest;
import exambyte.aggregates.studenten.StudiTest.TestForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StudiTestRepoImplTest {
    @Test
    @DisplayName("Der StudiTest mit ID = 1627 kann gespeichert und geladen werden")
    void test_01(){
        StudiTestRepoImplementation repoImpl = new StudiTestRepoImplementation();
        StudiTest studiTest = new StudiTest(new TestForm("Titel", null, null, null, 1627), null);
        repoImpl.saveTest(studiTest);
        assertThat(repoImpl.loadTestWithId(1627)).isEqualTo(studiTest);
    }

    @Test
    @DisplayName("Der StudiTest mit ID = 1627 kann gespeichert werden und die hasTestWithId Methode funktioniert.")
    void test_02(){
        StudiTestRepoImplementation repoImpl = new StudiTestRepoImplementation();
        StudiTest studiTest = new StudiTest(new TestForm("Titel", null, null, null, 1627), null);
        repoImpl.saveTest(studiTest);
        assertThat(repoImpl.hasTestWithId(1627)).isTrue();
    }

    @Test
    @DisplayName("Die loadTestList beinhaltet die gespeicherten Tests.")
    void test_03(){
        StudiTestRepoImplementation repoImpl = new StudiTestRepoImplementation();
        StudiTest studiTest = new StudiTest(new TestForm("Titel", null, null, null, 1627), null);
        repoImpl.saveTest(studiTest);
        assertThat(repoImpl.loadTestList()).contains(studiTest);
    }
}
