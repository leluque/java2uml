package br.com.luque.java2uml.writer;

import br.com.luque.java2uml.reflection.model.RelationshipField;
import br.com.luque.java2uml.reflection.model.ScopedClazz;

public interface RelationshipWriter {
    String getString(RelationshipField field);
    String getRealizationString(ScopedClazz scopedClazz);
    String getInheritanceString(ScopedClazz scopedClazz);
}
