package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Clazz;
import br.com.luque.java2uml.core.classdiagram.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.classdiagram.reflection.model.ScopedClazz;
import br.com.luque.java2uml.core.classdiagram.writer.RelationshipWriter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class YUMLRelationshipWriter implements RelationshipWriter {

    private final Set<RelationshipField> alreadyProcessedRelationships = new HashSet<>();

    public String getString(RelationshipField field) {
        return this.getString(field, null);
    }

    public String getString(RelationshipField field, RelationshipField otherSide) {
        Objects.requireNonNull(field);
        if (alreadyProcessedRelationships.contains(field)) {
            return "";
        }

        var result = new StringBuilder("[");
        result.append(YUMLClassWriter.getFormattedClassName(field.getClazz()));
        result.append("]");
        if (null != otherSide) {
            alreadyProcessedRelationships.add(otherSide);

            result.append(switch (otherSide.getCardinality()) {
                case ONE -> "1";
                case N -> "*";
            });
        }
        if (field.isAggregation()) {
            result.append("<>");
        } else if (field.isComposition()) {
            result.append("++");
        }
        result.append(switch (field.getCardinality()) {
            case ONE -> "->1";
            case N -> "->*";
        });

        result.append("[");
        result.append(YUMLClassWriter.getFormattedClassName(field.getOtherSide()));
        result.append("]");
        return result.toString();
    }

    public String getRealizationString(ScopedClazz scopedClazz) {
        Objects.requireNonNull(scopedClazz);
        if (!scopedClazz.hasInterfaces()) {
            return "";
        }
        var result = new StringBuilder();
        for (Clazz interface_ : scopedClazz.getInterfaces()) {
            result.append("[");
            result.append(YUMLClassWriter.getFormattedClassName(interface_));
            result.append("]^-.-[");
            result.append(YUMLClassWriter.getFormattedClassName(scopedClazz));
            result.append("]\n");
        }
        return result.toString();
    }

    public String getInheritanceString(ScopedClazz scopedClazz) {
        Objects.requireNonNull(scopedClazz);
        if (!scopedClazz.hasSuperclass()) {
            return "";
        }
        return "[" +
            YUMLClassWriter.getFormattedClassName(scopedClazz.getSuperclass()) +
            "]^[" +
            YUMLClassWriter.getFormattedClassName(scopedClazz) +
            "]\n";
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
            result.append("[");
            result.append(YUMLClassWriter.getFormattedClassName(scopedClazz));
            result.append("]-.->[");
            result.append(YUMLClassWriter.getFormattedClassName(dependency));
            result.append("]\n");
        }
        return result.toString();
    }
}
