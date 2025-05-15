package com.shyam.archunit;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.freeze.FreezingArchRule.freeze;

import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.library.GeneralCodingRules;
import java.util.Optional;
import java.util.function.Predicate;
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
  ArchRule NO_CLASSES_SHOULD_NOT_USE_FIELD_INJECTION =
      freeze(GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
          .because("classes should use constructor/setter injection instead field injection for better testability and "
              + "maintainability."));

  @ArchTest
  ArchRule CONTROLLER_CLASS_ANNOTATION_RULE =
      classes().that().resideInAPackage("..controller")
          .should().beAnnotatedWith(RestController.class)
          .andShould(new MaxMethodsCondition(4))
          .andShould(new MaxFieldsCondition(1));

  Predicate<JavaAnnotation<JavaMethod>> allowedCrudAnnotationForRestAPI = annotation ->
      annotation.getRawType().getDescription().contains("GetMapping")
          || annotation.getRawType().getDescription().contains("PostMapping")
          || annotation.getRawType().getDescription().contains("PutMapping")
          || annotation.getRawType().getDescription().contains("DeleteMapping");

  ArchCondition<JavaMethod> methodsAnnotatedWithCrudOperation =
      new ArchCondition<>("be annotated with @GetMapping/@PostMapping/@PutMapping/@DeleteMapping") {
        @Override
        public void check(JavaMethod item, ConditionEvents events) {
          Optional<JavaAnnotation<JavaMethod>> crudAnnotation = item.getAnnotations().stream()
              .filter(allowedCrudAnnotationForRestAPI).findFirst();
          if (crudAnnotation.isEmpty()) {
            String message = String.format("Method %s is not annotated with "
                + "@GetMapping/@PostMapping/@PutMapping/@DeleteMapping", item.getFullName());
            events.add(SimpleConditionEvent.violated(item, message));
          }
        }
      };

  @ArchTest
  ArchRule CONTROLLER_METHODS_SHOULD_PUBLIC_AND_CRUD_ANNOTATED_RULE =
      methods().that().areDeclaredInClassesThat().resideInAPackage("..controller")
          .should().bePublic()
          .andShould(methodsAnnotatedWithCrudOperation);

  @ArchTest
  ArchRule FIELDS_RESTRICTION_RULE = fields().should().notBePublic();
  @ArchTest
  ArchRule SERVICE_CLASSES_DEPENDENCY_RULE =
      classes().that().resideInAPackage("..service")
          .and().haveSimpleNameEndingWith("ServiceImpl")
          .should().beAnnotatedWith(Service.class)
          .andShould().onlyHaveDependentClassesThat().haveSimpleNameEndingWith("Service");


}
