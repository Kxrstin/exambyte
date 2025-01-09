package exambyte.persistence.studenten.repository;

import exambyte.aggregates.studenten.StudiTest.TestDaten;
import exambyte.persistence.studenten.data.StudiTestDto;
import exambyte.persistence.studenten.data.TestDatenDto;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudiTestDataRepository extends CrudRepository<StudiTestDto, Integer> {
    StudiTestDto findById(int id);
    boolean existsById(int id);
    List<StudiTestDto> findAll();
    StudiTestDto save(StudiTestDto test);

    @Modifying
    @Query("INSERT INTO studi_test (id, test_daten) VALUES (:id, :test_daten)")
    void insertNewTest(@Param("id") Integer id, @Param("test_daten") TestDatenDto daten);
}
