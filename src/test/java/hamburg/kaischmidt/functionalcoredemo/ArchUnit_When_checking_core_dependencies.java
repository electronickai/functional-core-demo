package hamburg.kaischmidt.functionalcoredemo;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.GeneralCodingRules.ACCESS_STANDARD_STREAMS;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "hamburg.kaischmidt")
public class ArchUnit_When_checking_core_dependencies {

    /**
     * Check that core functionality doesn't access shell code:
     */
    @ArchTest
    public static final ArchRule Then_core_must_not_depend_on_shell =
            noClasses().that().resideInAPackage("..core..")
                    .should().dependOnClassesThat().resideInAPackage("..shell..");

    /**
     * ...there may still be classes with side effects - like java.lang.System:
     */
    @ArchTest
    public static final ArchRule Then_core_must_not_access_standard_streams = noClasses().that().resideInAPackage("..core..").should(ACCESS_STANDARD_STREAMS);

}
