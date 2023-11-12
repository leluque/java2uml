package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.model.Field;
import br.com.luque.java2uml.reflection.model.RelationshipField;

import java.util.Objects;

public class YUMLAttributeWriter implements br.com.luque.java2uml.writer.AttributeWriter {
    public String getString(Field field) {
        Objects.requireNonNull(field);
        if (field instanceof RelationshipField) {
            throw new IllegalArgumentException("This writer is not for relationship fields.");
        }

        return YUMLHelper.getVisibilityText(field.getVisibility()) + field.getName() + ":" + field.getType().getName();
    }
}
