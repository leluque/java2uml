package br.com.luque.java2uml.plantuml.writer.classdiagram;

import br.com.luque.java2uml.plantuml.PlantUML;

public class ClazzWriterExample {

    public static void main(String[] args) {

        //PlantUML yUML = new PlantUML().doNotGenerateAccessors();
        PlantUML yUML = new PlantUML().generateAccessors();
        yUML.addPackages("br.com.luque.java2uml.example.virtualdriver.domain");
        System.out.println(yUML.generateClassDiagram());

    }

}
