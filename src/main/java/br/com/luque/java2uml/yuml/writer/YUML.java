package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.Java2UML;
import br.com.luque.java2uml.Rules;

@SuppressWarnings("unused")
public class YUML {
    private final Rules rules;

    public YUML() {
        this.rules = new Rules();
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
        return Java2UML.generateClassDiagramUsing(
            new YUMLClassWriter(
                new YUMLAttributeWriter(),
                new YUMLConstructorWriter(),
                new YUMLMethodWriter()
            ),
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
