package br.com.luque.java2uml.plantuml.writer.classdiagram;

import br.com.luque.java2uml.core.classdiagram.reflection.model.Field;
import br.com.luque.java2uml.core.classdiagram.reflection.model.RelationshipField;
import br.com.luque.java2uml.core.classdiagram.writer.FieldWriter;

import java.util.Objects;

public class PlantUMLFieldWriter implements FieldWriter {
    public String getString(Field field) {
        Objects.requireNonNull(field);
        if (field instanceof RelationshipField) {
            throw new IllegalArgumentException("This writer is not for relationship fields.");
        }

        return PlantUMLHelper.getVisibilityText(field.getVisibility()) + PlantUMLHelper.getStaticTextFor(field) + field.getName() + ":" + field.getType().getName();
    }
}
