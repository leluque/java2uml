package br.com.luque.java2uml.plantuml;

import br.com.luque.java2uml.Rules;
import br.com.luque.java2uml.plantuml.writer.classdiagram.*;

@SuppressWarnings("unused")
public class PlantUML {
    private final Rules rules;
    private boolean generateAccessors = false;

    public PlantUML() {
        this(false);
    }

    public PlantUML(boolean generateAccessors) {
        this.generateAccessors = generateAccessors;
        this.rules = new Rules();
    }

    public PlantUML generateAccessors() {
        this.generateAccessors = true;
        return this;
    }

    public PlantUML doNotGenerateAccessors() {
        this.generateAccessors = false;
        return this;
    }

    public void addPackages(String... packagesName) {
        rules.addPackages(packagesName);
    }

    public void addClasses(String... classesName) {
        rules.addClasses(classesName);
    }

    public void ignorePackages(String... packagesName) {
        rules.ignorePackages(packagesName);
    }

    public void ignoreClasses(String... classesName) {
        rules.ignoreClasses(classesName);
    }

    private String generateClassDiagram(Rules rules) {
        return PlantUMLWriter.generateClassDiagramUsing(
            new PlantUMLClassWriter(
                new PlantUMLFieldWriter(),
                new PlantUMLConstructorWriter(),
                new PlantUMLMethodWriter()
            ).generateAccessors(generateAccessors),
            new PlantUMLRelationshipWriter(),
            rules
        );
    }

    public String generateClassDiagram() {
        return generateClassDiagram(this.rules);
    }

    public String generateClassDiagram(String... packagesName) {
        Rules rules = new Rules();
        rules.addPackages(packagesName);
        return generateClassDiagram(rules);
    }
}
