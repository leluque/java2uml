package br.com.luque.java2uml.core.writer.classdiagram;

import br.com.luque.java2uml.core.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.reflection.model.ScopedClazz;

public interface RelationshipWriter {
    String getString(RelationshipField field);

    String getString(RelationshipField field, RelationshipField otherSide);

    String getRealizationString(ScopedClazz scopedClazz);

    String getInheritanceString(ScopedClazz scopedClazz);

    String getDependencyString(ScopedClazz scopedClazz);
}
