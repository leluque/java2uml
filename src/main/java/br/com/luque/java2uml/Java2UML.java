package br.com.luque.java2uml;

import br.com.luque.java2uml.classsearch.ClasspathSearcher;
import br.com.luque.java2uml.reflection.model.Clazz;
import br.com.luque.java2uml.reflection.model.RelationshipField;
import br.com.luque.java2uml.reflection.model.ScopedClazz;
import br.com.luque.java2uml.writer.ClassWriter;
import br.com.luque.java2uml.writer.RelationshipWriter;

import java.util.Objects;

public class Java2UML {
    public static String generateClassDiagramUsing(ClassWriter classWriter, RelationshipWriter relationshipWriter, Rules rules) {
        Objects.requireNonNull(classWriter);
        Objects.requireNonNull(relationshipWriter);
        Objects.requireNonNull(rules);

        ClasspathSearcher searcher = new ClasspathSearcher(rules);
        Class<?>[] classes = searcher.search();

        ClazzPool clazzPool = new ClazzPool(rules);
        for(Class<?> originalClass : classes) {
            clazzPool.getFor(originalClass);
        }

        String result = "";
        String relationshipResult = "";
        for(Clazz clazz : clazzPool.getScopedClazzes()) {
            ScopedClazz scopedClazz = (ScopedClazz) clazz;
            result += classWriter.getString(scopedClazz);
            result += "\n";

            for(RelationshipField field : (RelationshipField[]) scopedClazz.getRelationshipFields()) {
                relationshipResult += relationshipWriter.getString(field);
                relationshipResult += "\n";
                relationshipResult += relationshipWriter.getRealizationString(scopedClazz);
                relationshipResult += "\n";
                relationshipResult += relationshipWriter.getInheritanceString(scopedClazz);
            }
        }

        result += "\n" + relationshipResult;
        return result;
    }
}
