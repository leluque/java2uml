package br.com.luque.java2uml.core.classdiagram.writer;

import br.com.luque.java2uml.core.classdiagram.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.classdiagram.reflection.model.ScopedClazz;

public interface RelationshipWriter {
    String getString(RelationshipField field);

    String getString(RelationshipField field, RelationshipField otherSide);

    String getRealizationString(ScopedClazz scopedClazz);

    String getInheritanceString(ScopedClazz scopedClazz);

    String getDependencyString(ScopedClazz scopedClazz);
}
