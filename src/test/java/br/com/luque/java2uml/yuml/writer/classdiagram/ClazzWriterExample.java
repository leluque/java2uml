package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.yuml.YUML;

public class ClazzWriterExample {

    public static void main(String[] args) {

        //YUML yUML = new YUML().doNotGenerateAccessors();
        YUML yUML = new YUML().generateAccessors();
        yUML.addPackages("br.com.luque.java2uml.example.virtualdriver.domain");
        System.out.println(yUML.generateClassDiagram());

    }

}
