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
        for (Class<?> originalClass : classes) {
            clazzPool.getFor(originalClass);
        }

        StringBuilder result = new StringBuilder();
        StringBuilder relationshipResult = new StringBuilder();
        for (Clazz clazz : clazzPool.getScopedClazzes()) {
            ScopedClazz scopedClazz = (ScopedClazz) clazz;
            result.append(classWriter.getString(scopedClazz));
            result.append("\n");

            for (RelationshipField field : scopedClazz.getRelationshipFields()) {
                if (field.isMappedBy() && field.getOtherSide() instanceof ScopedClazz otherSideScopedClazz) {
                    relationshipResult.append(relationshipWriter.getString(field, otherSideScopedClazz.getRelationshipField(field.getMappedBy()).orElseThrow()));
                } else {
                    relationshipResult.append(relationshipWriter.getString(field));
                }
                relationshipResult.append("\n");
            }
            relationshipResult.append(relationshipWriter.getRealizationString(scopedClazz));
            relationshipResult.append(relationshipWriter.getInheritanceString(scopedClazz));
        }

        result.append("\n").append(relationshipResult);
        return result.toString().trim();
    }
}
