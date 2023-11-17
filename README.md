# Java2UML

## Overview

Java2UML is a library designed to generate UML class and sequence diagrams from Java classes using instrumentation, reflection, and class loading techniques.
This project simplifies the process of visualizing the structure and relationships of classes in a Java project, making
it useful for documentation and analysis purposes.

It currently transforms Java classes into [yUML](https://yuml.me) and PlantUML class diagrams. 

## How it works

Given a set of packages and exclusion rules, Java2UML will recursively search for all classes in those packages and
generate the diagram textual representation. This representation can then be used to generate a diagram.

## Getting Started

### Prerequisites

- Java 17 or higher
- A Java IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository.
2. Import the project into your Java IDE.
4. Build and run the project.

## Usage

### YUML Class diagram 

To generate an YUML class diagram, simply pass a set of packages and exclusion rules you wish.
```java
// Example code using the yUML facade.
String diagramText=YUML.generateClassDiagram("br.com.luque.java2uml.example.virtualdrive.domain","com.anotherpackage");

// You can also use exclusion rules.
YUML yUML=new YUML(); // Facade
yUML.addPackages("br.com.luque.java2uml.example.virtualdrive.domain","com.anotherpackage");
yUML.addClasses("java.lang.String","java.util.ArrayList");
yUML.ignoreClasses("br.com.luque.java2uml.example.virtualdrive.domain.FileSystemItem");
yUML.ignorePackages("br.com.luque.java2uml.example.virtualdrive.dto");
String diagramText=YUML.generateClassDiagram();
    
// You can avoid generating accessor methods creating an instance of YUML object using:
YUML yUML=new YUML().doNotGenerateAccessors();
```
### PlantUML class diagram

To generate a PlantUML class diagram, simply pass a set of packages and exclusion rules you wish.

```java
// Example code using the PlantUML facade.
String diagramText=PlantUML.generateClassDiagram("br.com.luque.java2uml.example.virtualdrive.domain","com.anotherpackage");

// You can also use exclusion rules.
PlantUML plantUML=new PlantUML(); // Facade
plantUML.addPackages("br.com.luque.java2uml.example.virtualdrive.domain","com.anotherpackage");
plantUML.addClasses("java.lang.String","java.util.ArrayList");
plantUML.ignoreClasses("br.com.luque.java2uml.example.virtualdrive.domain.FileSystemItem");
plantUML.ignorePackages("br.com.luque.java2uml.example.virtualdrive.dto");
String diagramText=plantUML.generateClassDiagram();
    
// You can avoid generating accessor methods creating an instance of PlantUML object using:
PlantUML plantUML=new PlantUML().doNotGenerateAccessors();
```

### PlantUML sequence diagram

Enter the project folder, replace the `path-to-java2uml-1.0.0.jar` with the path to the jar file and br.com.luque.Main by the project main class and run the following command:
    
    java -javaagent:"path-to-java2uml-1.0.0.jar=br.com.luque" br.com.luque.Main

## Room for improvement

- [ ] Add unit tests.
- [ ] Add support for other diagram types (e.g., object diagrams).
- [ ] Generate diagram images directly from the tool.
- [ ] Support execution as maven plugin to generate diagrams during build.
