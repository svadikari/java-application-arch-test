package com.shyam.archunit;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "com.shyam.archunit", importOptions = DoNotIncludeTests.class)
public class ArchitectureTest {


  @ArchTest
  ArchRule LAYERED_ARCH_RULE =
      layeredArchitecture()
          .consideringAllDependencies()
          .layer("Controller").definedBy("..controller..")
          .layer("Service").definedBy("..service..")
          .layer("Persistence").definedBy("..repository..")

          .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
          .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
          .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");

  @ArchTest
  ArchRule NO_CLASSES_USE_STANDARD_STREAMS = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

  @ArchTest
  ArchRule NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

  @ArchTest
  ArchRule NO_CLASSES_SHOULD_USE_JODA_TIME = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

  @ArchTest
  ArchRule NO_CLASSES_SHOULD_NOT_USE_FIELD_INJECTION =
      GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
          .because("classes should use constructor/setter injection instead field injection for better testability and "
              + "maintainability.");

  @ArchTest
  ArchRule CONTROLLER_CLASS_ANNOTATION_RULE =
      classes().that().resideInAPackage("..controller")
          .should().beAnnotatedWith(RestController.class);

  @ArchTest
  ArchRule SERVICE_PACKAGE_SHOULD_HAVE_ONLY_SERVICE_CLASSES_RULE =
      classes().that().resideInAPackage("..service")
          .should().haveSimpleNameEndingWith("Service")
          .orShould().haveSimpleNameEndingWith("ServiceImpl");

  @ArchTest
  ArchRule SERVICE_CLASSES_DEPENDENCY_RULE =
      classes().that().resideInAPackage("..service")
          .and().haveSimpleNameEndingWith("ServiceImpl")
          .should().beAnnotatedWith(Service.class)
          .andShould().onlyHaveDependentClassesThat().haveSimpleNameEndingWith("Service");

  @ArchTest
  ArchRule rule =
      classes().that().haveSimpleNameEndingWith("ServiceImpl")
          .should().onlyHaveDependentClassesThat().haveSimpleNameEndingWith("Service");
}
