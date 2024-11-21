package exambyte;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.assertj.ApplicationContextAssert;
import org.springframework.stereotype.Controller;

import java.lang.annotation.Annotation;
import exambyte.ExambyteProjektApplication;

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
            .adapter("infrastructure", "..infrastructure..")
            .adapter("persistence", "..persistence..");
}
