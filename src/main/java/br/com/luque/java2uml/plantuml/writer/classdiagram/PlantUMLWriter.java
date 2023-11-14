package br.com.luque.java2uml.plantuml.writer.classdiagram;

import br.com.luque.java2uml.ClazzPool;
import br.com.luque.java2uml.Rules;
import br.com.luque.java2uml.core.classsearch.ClasspathSearcher;
import br.com.luque.java2uml.core.reflection.model.Clazz;
import br.com.luque.java2uml.core.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.reflection.model.ScopedClazz;

import java.util.Objects;

public class PlantUMLWriter {
    public static String generateClassDiagramUsing(PlantUMLClassWriter classWriter, PlantUMLRelationshipWriter relationshipWriter, Rules rules) {
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
        result.append("@startuml\n");
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
            relationshipResult.append(relationshipWriter.getDependencyString(scopedClazz));
        }

        result.append("\n").append(relationshipResult);
        result.append("\n@enduml");
        return result.toString().trim();
    }
}
