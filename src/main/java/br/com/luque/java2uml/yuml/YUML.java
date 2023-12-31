package br.com.luque.java2uml.yuml;

import br.com.luque.java2uml.Rules;
import br.com.luque.java2uml.yuml.writer.classdiagram.*;

@SuppressWarnings("unused")
public class YUML {
    private final Rules rules;
    private boolean generateAccessors = false;

    public YUML() {
        this(false);
    }

    public YUML(boolean generateAccessors) {
        this.generateAccessors = generateAccessors;
        this.rules = new Rules();
    }

    public YUML generateAccessors() {
        this.generateAccessors = true;
        return this;
    }

    public YUML doNotGenerateAccessors() {
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
        return YUMLWriter.generateClassDiagramUsing(
            new YUMLClassWriter(
                new YUMLFieldWriter(),
                new YUMLConstructorWriter(),
                new YUMLMethodWriter()
            ).generateAccessors(generateAccessors),
            new YUMLRelationshipWriter(),
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
