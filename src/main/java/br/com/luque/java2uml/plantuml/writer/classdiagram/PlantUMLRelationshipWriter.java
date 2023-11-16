package br.com.luque.java2uml.plantuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Clazz;
import br.com.luque.java2uml.core.classdiagram.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.classdiagram.reflection.model.ScopedClazz;
import br.com.luque.java2uml.core.classdiagram.writer.RelationshipWriter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class PlantUMLRelationshipWriter implements RelationshipWriter {

    private final Set<RelationshipField> alreadyProcessedRelationships = new HashSet<>();

    public String getString(RelationshipField field) {
        return this.getString(field, null);
    }

    public String getString(RelationshipField field, RelationshipField otherSide) {
        Objects.requireNonNull(field);
        if (alreadyProcessedRelationships.contains(field)) {
            return "";
        }

        var result = new StringBuilder(field.getClazz().getName());
        if (null == otherSide) {
            result.append(" ");
        } else {
            alreadyProcessedRelationships.add(otherSide);
            result.append(PlantUMLHelper.getCardinalityTextFor(otherSide.getCardinality()));
        }
        if (field.isAggregation()) {
            result.append("o");
        } else if (field.isComposition()) {
            result.append("*");
        }
        result.append("-->");
        result.append(PlantUMLHelper.getCardinalityTextFor(field.getCardinality()));
        result.append(field.getOtherSide().getName());
        return result.toString();
    }

    public String getRealizationString(ScopedClazz scopedClazz) {
        Objects.requireNonNull(scopedClazz);
        if (!scopedClazz.hasInterfaces()) {
            return "";
        }
        var result = new StringBuilder();
        for (Clazz interface_ : scopedClazz.getInterfaces()) {
            result.append(scopedClazz.getName());
            result.append(" ..|> ");
            result.append(interface_.getName());
            result.append("\n");
        }
        return result.toString();
    }

    public String getInheritanceString(ScopedClazz scopedClazz) {
        Objects.requireNonNull(scopedClazz);
        if (!scopedClazz.hasSuperclass()) {
            return "";
        }
        return scopedClazz.getSuperclass().getName() +
            " <|-- " +
            scopedClazz.getName() +
            "\n";
    }

    public String getDependencyString(ScopedClazz scopedClazz) {
        Objects.requireNonNull(scopedClazz);
        Clazz[] dependencies = scopedClazz.getDependencies();
        if (null == dependencies || dependencies.length == 0) {
            return "";
        }

        dependencies = Stream.of(dependencies).filter(
            d -> !scopedClazz.hasAssociationOfAnyTypeWith(d)
                && !scopedClazz.equals(d)
        ).toArray(Clazz[]::new);

        var result = new StringBuilder();
        for (Clazz dependency : dependencies) {
            result.append(scopedClazz.getName());
            result.append(" ..> ");
            result.append(dependency.getName());
            result.append("\n");
        }
        return result.toString();
    }
}
