I'll create a README document for your project. Based on the code you've provided, here's a comprehensive README:

# Java Application Architecture Testing Demo

This project demonstrates architectural testing in a Java Spring Boot application using ArchUnit. It shows how to enforce architectural rules and coding standards in a multi-module application.

## Project Structure

The project consists of three main modules:
- `orders`: Order management functionality
- `products`: Product management functionality
- `arch-testing-automation`: Architecture testing rules and configurations

## Technologies Used

- Java 24
- Spring Boot 3.4.5
- Gradle
- PostgreSQL
- Lombok
- SpringDoc OpenAPI
- ArchUnit for architectural testing

## Key Features

### Architecture Testing

The project demonstrates various architectural validations:
- Layered architecture enforcement (Controller → Service → Repository)
- Naming conventions for services and implementations
- Dependency injection best practices
- Package access restrictions

### Example Architecture Rules
- Repository must accessed by Service, Service accessed by Controller and Controller shouldn't be accessed by layer from project
- Controllers must be annotated with `@RestController`
- Service classes must have names ending with "Service" or "ServiceImpl"
- ServiceImpl classes should only be accessed by Service interfaces
- Classes with suffix "Impl" in service package can only access repository package classes
- No field injection (constructor injection preferred)

To implement architectural rules using ArchUnit, you can define rules in a test class and validate them against your codebase. Here's a detailed explanation of how to implement the rules:

### 1. **Layered Architecture**
This rule enforces a strict dependency flow between layers (e.g., Controller → Service → Repository).

```java
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
```

### 2. **Class Annotations**
Ensure specific classes are annotated correctly, e.g., Controllers with `@RestController` and Services with `@Service`.

```java
@ArchTest
ArchRule CONTROLLER_CLASS_ANNOTATION_RULE =
    classes().that().resideInAPackage("..controller")
        .should().beAnnotatedWith(RestController.class);

@ArchTest
ArchRule SERVICE_CLASSES_DEPENDENCY_RULE =
    classes().that().resideInAPackage("..service")
        .and().haveSimpleNameEndingWith("ServiceImpl")
        .should().beAnnotatedWith(Service.class)
        .andShould().onlyHaveDependentClassesThat().haveSimpleNameEndingWith("Service");
```

### 3. **Restrict Access Between Packages**
Restrict access to ensure classes in the `service` package with the suffix `Impl` only access classes in the `repository` package.

```java
@ArchTest
public static final ArchRule SERVICE_IMPL_CLASSES_SHOULD_ONLY_ACCESS_REPOSITORY_CLASSES =
    classes().that().resideInAPackage("..service..").and().haveSimpleNameEndingWith("Impl")
        .should().onlyAccessClassesThat().resideInAPackage("..repository..");
```

### 4. **General Coding Rules**
Avoid bad practices like using standard streams or field injection.

```java
@ArchTest
ArchRule NO_CLASSES_USE_STANDARD_STREAMS = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

@ArchTest
ArchRule NO_CLASSES_SHOULD_NOT_USE_FIELD_INJECTION =
    GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
        .because("Constructor injection is preferred for better testability.");
```

### 4. **Customer Coding Rules**
Restrict REST API to use limited annotations like GET, POST, PUT and DELETE.
```java
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
```
### 5. **Freezing existing violations**
Accepting existing violations, freezing them to resolve in the future. No new violations should be introduced.

```java
@ArchTest
ArchRule NO_CLASSES_SHOULD_NOT_USE_FIELD_INJECTION =
    freeze(GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
        .because("Constructor injection is preferred for better testability."));
```

### 6. **Run the Tests**
Place these rules in a test class (e.g., `ArchitectureTest`) and run it as a JUnit test. ArchUnit will validate the rules against your codebase.

By following these steps, you can enforce architectural constraints and maintain a clean, consistent structure in your project.

## Getting Started

### Prerequisites

- Java 24 JDK
- Gradle
- PostgreSQL database

### Building the Project

```bash
./gradlew build
```

### Running the Application

```bash
./gradlew bootRun
```

### API Endpoints

The service provides REST endpoints for order management:

- `POST /orders` - Create a new order
- `GET /orders` - Get all orders
- `GET /orders/{id}` - Get order by ID

## Architecture Testing

Run the architecture tests:

```bash
./gradlew test
```

These tests validate that the codebase adheres to the defined architectural constraints and coding standards.

## Project Dependencies

The project uses Spring Boot starter dependencies along with:
- Spring Data JPA for data access
- PostgreSQL driver for database connectivity
- Lombok for reducing boilerplate code
- SpringDoc OpenAPI for API documentation
- ArchUnit for architecture testing

    ` testImplementation 'com.tngtech.archunit:archunit-junit5:1.4.0'`

## License

[Add your license here]