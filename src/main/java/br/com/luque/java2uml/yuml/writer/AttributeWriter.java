package br.com.luque.java2uml.yuml.writer;

import br.com.luque.java2uml.reflection.Field;
import br.com.luque.java2uml.reflection.RelationshipField;

import java.util.Objects;

public class AttributeWriter {
	public String getString(Field field) {
		Objects.requireNonNull(field);
		if(field instanceof RelationshipField) {
			throw new IllegalArgumentException("This writer is not for relationship fields.");
		}

		String result = YUMLHelper.getVisibilityText(field.getVisibility());
		result += field.getName();
		result += ":";
		result += field.getType().getName();
		return result;
	}
}
