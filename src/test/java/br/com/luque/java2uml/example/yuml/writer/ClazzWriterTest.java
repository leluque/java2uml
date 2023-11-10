package br.com.luque.java2uml.example.yuml.writer;

import br.com.luque.java2uml.example.virtualdrive.domain.Folder;
import br.com.luque.java2uml.reflection.Clazz;
import br.com.luque.java2uml.reflection.ClazzPool;
import br.com.luque.java2uml.reflection.Rules;
import br.com.luque.java2uml.reflection.ScopedClazz;
import br.com.luque.java2uml.yuml.writer.AttributeWriter;
import br.com.luque.java2uml.yuml.writer.ClassWriter;
import br.com.luque.java2uml.yuml.writer.ConstructorWriter;
import br.com.luque.java2uml.yuml.writer.MethodWriter;

public class ClazzWriterTest {

    public static void main(String[] args) {

        Rules rules = new Rules();
        rules.addPackages("br.com.luque.java2uml.example.virtualdrive.domain");
        ClazzPool pool = new ClazzPool(rules);

        Clazz folderClass = new ScopedClazz(Folder.class, pool);
        ClassWriter classWriter = new ClassWriter(
                new AttributeWriter(),
                new ConstructorWriter(),
                new MethodWriter()
        );
        System.out.println(classWriter.getString(folderClass));

    }

}
