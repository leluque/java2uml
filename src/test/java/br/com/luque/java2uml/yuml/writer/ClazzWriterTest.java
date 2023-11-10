package br.com.luque.java2uml.yuml.writer;

public class ClazzWriterTest {

    public static void main(String[] args) {

        YUML yUML = new YUML();
        yUML.addPackages("br.com.luque.java2uml.example.virtualdriver.domain");
        System.out.println(yUML.generateClassDiagram());

    }

}
