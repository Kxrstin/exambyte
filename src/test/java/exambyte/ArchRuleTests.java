package exambyte;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import exambyte.annotations.AggregateRoot;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packagesOf = ExambyteProjektApplication.class)
public class ArchRuleTests {
    @ArchTest
    private final ArchRule nurControllerInControllerKlassen = classes()
            .that()
            .resideInAPackage("..controller..")
            .and()
            .haveSimpleNameContaining("Controller")
            .should()
            .beAnnotatedWith(Controller.class)
            .orShould()
            .beAnnotatedWith(WebMvcTest.class);

    @ArchTest
    private final ArchRule onionArchitecture = onionArchitecture()
            .domainModels("..aggregates..")
            .applicationServices("..service..")
            .domainServices("..domainService..")
            .adapter("web", "..controller..")
            .adapter("persistence", "..persistence..");

    @ArchTest
    private final ArchRule jedesAggregatRootIstAnnotiert = classes()
            .that()
            .resideInAPackage("..aggregates..")
            .should()
            .beAnnotatedWith(AggregateRoot.class)
            .orShould()
            .notBePublic();

    @ArchTest
    private final ArchRule serviceKlassenSindMitServiceAnnotiert = classes()
            .that()
            .resideInAPackage("..service..")
            .and()
            .haveSimpleNameContaining("Service")
            .should()
            .beAnnotatedWith(Service.class);

    @ArchTest
    private final ArchRule repositoriesSindMitRepositoryAnnotiert = classes()
            .that()
            .resideInAPackage("..persistence..")
            .and()
            .haveSimpleNameContaining("Repo")
            .should()
            .beAnnotatedWith(Repository.class);
}
