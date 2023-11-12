# Java2UML

## Overview

Java2UML is a tool designed to generate class diagrams from Java classes using reflection and class loading techniques.
This project simplifies the process of visualizing the structure and relationships of classes in a Java project, making
it useful for documentation and analysis purposes.

It currently transforms Java classes into [yUML](https://yuml.me) class diagram code, but may be extended to other tools
in the future, such as PlantUML.

## How it works

Given a set of packages and exclusion rules, Java2UML will recursively search for all classes in those packages and
generate a yUML class diagram code. This code can then be used to generate a visual class diagram using the
[yUML](https://yuml.me) tool.

## Getting Started

### Prerequisites

- Java 17 or higher
- A Java IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository.
2. Import the project into your Java IDE.
4. Build and run the project.

## Usage

To use Java2UML, simply pass a set of packages and exclusion rules you wish to generate the textual representation for
a class diagram.

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
```

## Room for improvement

- [ ] Add unit tests.
- [ ] Add support for other diagram tools (e.g., PlantUML).
- [ ] Add support for other diagram types (e.g., object diagrams).
- [ ] Generate diagram images directly from the tool.
- [ ] Support execution as maven plugin to generate diagrams during build.