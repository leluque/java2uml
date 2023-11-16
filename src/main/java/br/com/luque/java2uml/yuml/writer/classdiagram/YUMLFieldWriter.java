package br.com.luque.java2uml.yuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Field;
import br.com.luque.java2uml.core.classdiagram.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.classdiagram.writer.FieldWriter;

import java.util.Objects;

public class YUMLFieldWriter implements FieldWriter {
    public String getString(Field field) {
        Objects.requireNonNull(field);
        if (field instanceof RelationshipField) {
            throw new IllegalArgumentException("This writer is not for relationship fields.");
        }

        return YUMLHelper.getVisibilityText(field.getVisibility()) + field.getName() + ":" + field.getType().getName();
    }
}
